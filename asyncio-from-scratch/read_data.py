import logging
import selectors
import socket
import time
import select

import waterball
from async_http_server import SimpleAsyncHttpServer

logger = logging.getLogger(__name__)
app = SimpleAsyncHttpServer()


def read_data_from_url(url: str, port: int) -> str:
    # Extract the host and path from the URL
    host = url.split('//')[-1].split('/')[0]
    path = url.split(host)[-1]
    path = '/' if path == '' else path

    # Create a socket connection to the host
    f = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
    f.setblocking(False)  # Non-blocking mode

    f.connect_ex((host, port))  # Non-blocking connect (use connect_ex to avoid blocking)

    # 使用 select 監控可寫的狀態
    _, writable, _ = select.select([], [f], [], 5)  # 5 秒超時
    if writable:
        # 檢查 socket 狀態是否有錯誤
        err = f.getsockopt(socket.SOL_SOCKET, socket.SO_ERROR)
        if err == 0:
            print("連線成功")
        else:
            print(f"連線失敗，錯誤碼: {err}")
    else:
        print("連線超時")

    # Create the HTTP GET request
    request = f"GET {path} HTTP/1.1\nHost: {host}\nConnection: close\n\n"
    request_encoded = request.encode('utf-8')
    logger.debug(f"Opening the connection by '{request_encoded}'...")
    f.sendall(request_encoded)  # Send the request to the server
    logger.debug(f"Connection opened.")

    # 開始讀取回應    
    future = waterball.Future()

    result = ""

    def receive_data():
        nonlocal result, f
        frame = f.recv(1024)
        content = frame.decode('utf-8')
        logger.debug(f"Next frame: '{content}'")
        result += content

        has_next_data = f.recv(1, socket.MSG_PEEK)  # peek next data
        # When recv() returns b'', it indicates the socket has been closed by the remote peer.
        if not has_next_data:
            logger.debug("Connection closed")
            waterball.unregister(f)
            future.set_result(result)

    waterball.register(f, selectors.EVENT_READ, receive_data)  # 註冊 f 的可讀事件
    yield from future
    return result

@app.get("/")
def index():
    yield from waterball.sleep(3)
    return "Hello"

def main():
    yield from waterball.sleep(1)
    waterball.create_task(app.serve("localhost", 65432))
    page_content = yield from read_data_from_url("http://localhost", 65432)
    print(page_content)


if __name__ == '__main__':
    logging.basicConfig(
        level=logging.DEBUG,
        format='%(asctime)s - %(name)s - %(levelname)s - %(message)s',
    )
    waterball.run(main())
