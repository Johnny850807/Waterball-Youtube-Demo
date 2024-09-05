import asyncio
import logging
from asyncio.selector_events import BaseSelectorEventLoop

import waterball
from async_http_server import SimpleAsyncHttpServer

app = SimpleAsyncHttpServer()


def process_message(message: str):
    yield from waterball.sleep(3)
    yield from waterball.sleep(3)
    yield from waterball.sleep(3)
    return f"Echo: {message}"


def main():
    yield from process_message("1")
    yield from process_message("2")
    yield from process_message("3")


@app.get("/api/health")
def health_check(message: str):
    yield from process_message(message)


if __name__ == '__main__':
    logging.basicConfig(
        level=logging.DEBUG,
        format='%(asctime)s - %(name)s - %(levelname)s - %(message)s',
    )
    waterball.run(main())
    waterball.run(app.serve("localhost", 65432))
