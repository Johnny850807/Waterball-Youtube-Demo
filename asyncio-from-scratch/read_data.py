import logging
import random
import select
import selectors
import socket

import waterball
from async_http_server import SimpleAsyncHttpServer

logger = logging.getLogger(__name__)
app = SimpleAsyncHttpServer()

_num_read_data_task = 0


def _increment_num_read_data_task():
    global _num_read_data_task
    _num_read_data_task += 1
    return _num_read_data_task


def read_data_from_url(url: str, port: int) -> str:
    # Extract the host and path from the URL
    host = url.split('//')[-1].split('/')[0]
    path = url.split(host)[-1]
    path = '/' if path == '' else path

    # Create a socket connection to the host
    f = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
    f.setblocking(False)  # Non-blocking mode

    f.connect_ex((host, port))  # Non-blocking connect (use connect_ex to avoid blocking)
    
    name = f"Read data #{_increment_num_read_data_task()}"

    connected_future = waterball.Future()
    
    def on_connected():
        nonlocal connected_future
        connected_future.set_result(None)
        waterball.unregister(f)

    waterball.register(f, selectors.EVENT_WRITE, on_connected, name)  # 註冊 f 的可讀事件

    yield from connected_future

    # Create the HTTP GET request
    request = f"GET {path} HTTP/1.1\nHost: {host}\nConnection: close\n\n"
    request_encoded = request.encode('utf-8')
    logger.debug(f"Opening the connection by '{request_encoded}'...")
    f.sendall(request_encoded)  # Send the request to the server
    logger.debug(f"Connection opened.")

    # 開始讀取回應    
    result_future = waterball.Future()
    result = ""

    def receive_data():
        nonlocal result
        frame = f.recv(2)
        content = frame.decode('utf-8')
        logger.debug(f"Next frame: '{content}'")
        result += content

        has_next_data = f.recv(1, socket.MSG_PEEK)  # peek next data
        # When recv() returns b'', it indicates the socket has been closed by the remote peer.
        if not has_next_data:
            logger.debug("Connection closed")
            waterball.unregister(f)
            result_future.set_result(result)

    waterball.register(f, selectors.EVENT_READ, receive_data, name)  # 註冊 f 的可讀事件
    return (yield from result_future)


@app.get("/")
def index():
    return "Hello"


@app.get("/stop_server")
def index():
    yield from waterball.sleep(3)
    logger.debug("Stopping server")
    yield from app.stop()  # TODO: didn't work
    logger.debug("Server stopped")
    return "Server stopped"


r = random.Random()


def read_data():
    # yield from waterball.sleep(r.randint(0, 5))
    page_content = yield from read_data_from_url("http://waterballsa.tw", 80)
    return page_content


def main():
    # yield from waterball.sleep(1)
    # app.serve("localhost", 65432)
    # page_content = yield from read_data_from_url("http://localhost", 65432)
    # print(page_content)
    coros = [read_data] * 500
    results = yield from waterball.gather(*[coro() for coro in coros])
    print(results)


if __name__ == '__main__':
    logging.basicConfig(
        level=logging.INFO,
        format='%(asctime)s - %(name)s - %(levelname)s - %(message)s',
    )

    waterball.run(main())
    # waterball.run(read_data())
    waterball.draw_stats(r"Read data")
