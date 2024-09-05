import asyncio
import heapq
import logging
import selectors
from datetime import datetime, timedelta
from types import GeneratorType

import stats
import waterball

logger = logging.getLogger(__name__)

MAXIMUM_SELECT_TIMEOUT = 24 * 3600  # Maximum timeout passed to select to avoid OS limitations


class Handle:
    def __init__(self, callback, name: str, *args):
        self.name = name
        self.callback = callback
        self.args = args

    def __call__(self):
        return self.callback(*self.args)


class TimeHandle(Handle):
    def __init__(self, when: datetime, callback, name: str, *args):
        super().__init__(callback, name, *args)
        self.when = when


class EventLoop:
    def __init__(self, selector=None) -> None:
        selector = selector or selectors.DefaultSelector()
        self._selector = selector
        self._scheduled = []
        self._ready = []
        self.running = False
        self.stats = stats.Stats()

    def schedule_task(self, task):
        self.call_soon(task.step, name=task.name)
        return task

    def call_later(self, delay: int, callback, *args, name: str = None):
        current_time = datetime.now()
        new_time = current_time + timedelta(seconds=delay)
        heapq.heappush(self._scheduled, (new_time, TimeHandle(new_time, callback, name, *args)))

    def call_soon(self, callback, *args, name=None):
        name = name or getattr(callback, 'name', None) or callback.__name__
        handle = Handle(callback, name, *args) if not isinstance(callback, Handle) else callback
        self._ready.append(handle)

    def register(self, fileobj, event_mask, callback):
        self._selector.register(fileobj, event_mask, data=callback)

    def unregister(self, fileobj):
        self._selector.unregister(fileobj)

    def run_until_complete(self, coro_or_future=None):
        coro = coro_or_future if isinstance(coro_or_future, GeneratorType) else coro_or_future.__await__()
        task = Task(coro, loop=self)
        task.add_done_callback(self._run_until_complete_callback)
        self.schedule_task(task)
        try:
            self.run_forever()
        finally:
            task.remove_done_callback(self._run_until_complete_callback)

    def _run_until_complete_callback(self, future):
        self.stop()

    def run_forever(self):
        self.running = True
        while self.running:
            if len(self._scheduled) != 0:
                scheduled_time, handle = self._scheduled[0]
                if scheduled_time <= datetime.now():
                    _, handle = heapq.heappop(self._scheduled)
                    self.call_soon(handle)
            events = self._selector.select(0.01)
            self._process_events(events)
            if len(self._ready) != 0:
                handle = self._ready.pop()
                self.stats.start_task_step(handle.name)
                handle()
                self.stats.end_task_step(handle.name)

    def stop(self):
        logger.info('Stop Event Loop')
        self.running = False

    def _process_events(self, events):
        logger.debug(f"Selected Events: len={len(events)}")
        for key, mask in events:
            fileobj, callback = key.fileobj, key.data
            self.call_soon(callback)


_PENDING = "PENDING"
_CANCELLED = "CANCELLED"
_FINISHED = "FINISHED"


class Future:
    def __init__(self, result=None, loop: EventLoop = None):
        self._state = _PENDING if result is None else _FINISHED
        self._result = result
        self._exception = None
        self._callbacks = []
        self._loop = loop if loop is not None else waterball.get_event_loop()

    @property
    def done(self):
        return self._state is not _PENDING

    @property
    def result(self):
        return self._result

    @property
    def exception(self):
        return self._exception

    def set_result(self, result):
        self._result = result
        self._state = _FINISHED
        self.__schedule_callbacks()

    def set_exception(self, exception):
        self._exception = exception
        self._state = _CANCELLED
        self.__schedule_callbacks()

    def add_done_callback(self, callback):
        if self.done:
            self._loop.call_soon(callback, self)
        else:
            self._callbacks.append(callback)

    def remove_done_callback(self, callback):
        if callback in self._callbacks:
            self._callbacks.remove(callback)

    def __schedule_callbacks(self):
        callbacks = self._callbacks[:]

        if not callbacks:
            return

        self._callbacks[:] = []
        for callback in callbacks:
            self._loop.call_soon(callback, self)

    def __await__(self):
        if not self.done:
            # 第一次 await Future 時 -- 如果還沒完成，先將 future 自己 yield 出去，
            # Task 那邊收到 yield 的 result 若是 Future 會去偵聽 future 的完成事件 (via add_done_callback)，
            # 確認 future 完成後才會再呼叫一次這個 coro 繼續往下執行
            yield self
        # 呈上，預期收到 future 完成事件後才會執行到這邊，所以如果此時還判斷出 not done() 就代表有鬼
        if not self.done:
            raise RuntimeError("await wasn't used with future")
        return self.result

    __iter__ = __await__


_num_of_tasks = 0


def _increment_task_count() -> int:
    global _num_of_tasks
    _num_of_tasks = _num_of_tasks + 1
    return _num_of_tasks


class Task(Future):
    def __init__(self, coro, loop: EventLoop, name=None):
        super().__init__()
        count = _increment_task_count()
        self.__log = logger.getChild(f"{self.__class__.__name__}({name})")
        self.name = name or f"Task {count}"
        self.coro = coro
        self.loop = loop

    def step(self, err=None):
        try:
            self.__log.debug("Next step")
            # 回溯：繼續執行 coroutine 的下一個 frame
            result = self.coro.send(None)
        except StopIteration as e:
            self.__log.debug("StopIteration")
            self.set_result(e.value)
        else:
            if isinstance(result, Future):
                # 接收到 Future -> 代表 coroutine 正在等待一個未來某時才會完成的值被處理完
                # 於是這裡偵聽 Future 的完成事件，完成之後 wake up 
                result.add_done_callback(self.__wake_up)

    def __wake_up(self, future):
        try:
            # 確認 future 完成之後，Task 要醒來繼續執行下一個 frame
            # 於是先判斷一下 future 完成是：異常狀態還是正常完成
            future.result
        except BaseException as err:
            # 若是異常狀態，則將例外送入 coroutine (呼叫 coro.throw(err))
            self.step(err)
        else:
            self.step()
