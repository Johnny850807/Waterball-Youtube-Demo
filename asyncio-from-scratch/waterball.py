import logging
import threading
import types

from core import EventLoop, Future, Task

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


def run(coro):
    if not isinstance(coro, types.GeneratorType):
        raise TypeError(f"The object {coro} must be a Generator.")

    if get_running_loop() is not None:
        raise RuntimeError("waterball.run() cannot be called from a running event loop")

    loop = get_event_loop()
    return loop.run_until_complete(coro)


def sleep(seconds: int):
    loop = get_running_loop()
    future = Future()
    loop.call_later(seconds, future.set_result,  "Complete Sleeping", name="Sleeping")
    return future


def schedule_task(coro, name=None):
    loop = get_event_loop()
    task = Task(coro, loop, name=name)
    loop.schedule_task(task)


def register(fileobj, event_mask, callback):
    loop = get_event_loop()
    loop.register(fileobj, event_mask, callback)


def unregister(fileobj):
    loop = get_event_loop()
    loop.unregister(fileobj)
