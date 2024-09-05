import heapq
import logging
from datetime import datetime, timedelta

logger = logging.getLogger(__name__)


class EventLoop:
    def __init__(self) -> None:
        self._scheduled = []
        self._ready = []
        self.running = False

    def create_task(self, coro):
        task = Task(coro, loop=self)
        self.call_soon(task.step)
        return task

    def call_later(self, delay: int, callback, *args):
        current_time = datetime.now()
        new_time = current_time + timedelta(seconds=delay)
        heapq.heappush(self._scheduled, (new_time, callback, args))

    def call_soon(self, callback, *args):
        self._ready.append((callback, args))

    def run_forever(self):
        self.running = True
        while self.running:
            if len(self._scheduled) != 0:
                (scheduled_time, callback, *args) = self._scheduled[0]
                if scheduled_time <= datetime.now():
                    (scheduled_time, callback, *args) = heapq.heappop(self._scheduled)
                    self.call_soon(callback, *args)

            if len(self._ready) != 0:
                callback, args = self._ready.pop()
                callback(*args)


class Future:
    def __init__(self):
        self.done = False
        self.result = None

    def set_result(self, result):
        self.result = result
        self.done = True

    def __await__(self):
        while not self.done:
            yield self
        return self.result

    __iter__ = __await__

    @classmethod
    def done(cls, result=True):
        f = Future()
        f.set_result(result)
        return f


class Task(Future):
    def __init__(self, coro, loop: EventLoop):
        super().__init__()
        self.__log = logger.getChild(self.__class__.__name__)
        self.coro = coro
        self.loop = loop
        self.done = False

    def step(self):
        try:
            self.__log.debug("Next step")
            result = self.coro.send(None)
        except StopIteration as e:
            self.set_result(e.value)
        else:
            # Future ->
            if isinstance(result, Future):
                self.loop.call_soon(self.step)
