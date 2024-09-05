import logging

import waterball
from async_http_server import SimpleAsyncHttpServer

app = SimpleAsyncHttpServer()


def process_message(message: str):
    yield from waterball.sleep(3)
    yield from waterball.sleep(3)
    yield from waterball.sleep(3)
    return f"Echo: {message}"


def async_generator():
    yield 1
    yield from waterball.sleep(3)
    yield 2
    yield from waterball.sleep(3)
    yield 3
    yield from waterball.sleep(3)
    yield 4


def main():
    for x in async_generator():
        yield from waterball.sleep(3)
        print("Notice!!!" * 20 + str(x))


@app.get("/api/health")
def health_check(message: str):
    yield from process_message(message)


if __name__ == '__main__':
    logging.basicConfig(
        level=logging.DEBUG,
        format='%(asctime)s - %(name)s - %(levelname)s - %(message)s',
    )
    waterball.run(main())
    # waterball.run(app.serve("localhost", 65432))
