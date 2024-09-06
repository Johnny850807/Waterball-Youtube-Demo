import logging
import threading
import types

from core import EventLoop, Future, Task, Handle

# Create a logger instance
logger = logging.getLogger(__name__)

thread_local = threading.local()


def get_running_loop() -> EventLoop | None:
    if hasattr(thread_local, 'loop'):
        return thread_local.loop
    return None


def get_event_loop() -> EventLoop | None:
    loop = get_running_loop()
    if loop is None:
        loop = thread_local.loop = EventLoop()
    return loop


def run(coro_or_future):
    if not isinstance(coro_or_future, types.GeneratorType) and not isinstance(coro_or_future, Future):
        raise TypeError(f"The object {coro_or_future} must be a Generator or a Future.")

    if get_running_loop() is not None:
        raise RuntimeError("waterball.run() cannot be called from a running event loop")

    loop = get_event_loop()
    loop.run_until_complete(coro_or_future)


def sleep(seconds: int):
    loop = get_running_loop()
    future = Future()
    loop.call_later(seconds, future.set_result, "Complete Sleeping", name="Sleeping")
    return future


def gather(*coro_or_futures) -> Future:
    f = Future()
    loop = get_event_loop()
    tasks = []
    results = [None] * len(coro_or_futures)

    def check_if_all_tasks_done(finished):
        if finished in tasks:
            index = tasks.index(finished)
            results[index] = finished.result
            n_completed = len([t for t in tasks if t.done])
            if n_completed == len(tasks):
                f.set_result(results)

    for coro in coro_or_futures:
        coro = coro if isinstance(coro, types.GeneratorType) else coro.__await__()
        task = Task(coro, loop)
        tasks.append(task)
        task.add_done_callback(check_if_all_tasks_done, name="Gather Tasks")
        loop.schedule_task(task)
    return f


def schedule_task(coro, name=None):
    loop = get_event_loop()
    task = Task(coro, loop, name=name)
    loop.schedule_task(task)


def register(fileobj, event_mask, callback, name=None):
    loop = get_event_loop()
    loop.register(fileobj, event_mask, Handle(callback, name))


def unregister(fileobj):
    loop = get_event_loop()
    loop.unregister(fileobj)


def draw_stats(task_name_regex=None):
    loop = get_event_loop()
    loop.stats.draw(task_name_regex)
