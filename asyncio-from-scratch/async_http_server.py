import inspect
import logging
import selectors
import socket

import waterball
from core import Future

logger = logging.getLogger(__name__)


class SimpleAsyncHttpServer:
    def __init__(self):
        self.routes = {}
        self.running = False
        self._selector = None
        self._server_socket = None

    def accept_connection(self, server_socket):
        client_socket, addr = server_socket.accept()
        print(f"Accepted connection from {addr}")
        client_socket.setblocking(False)
        self._selector.register(client_socket, selectors.EVENT_READ, data=client_socket)

    def handle_client(self, client_socket):
        selector = self._selector

        try:
            data = client_socket.recv(1024)
            
            request_lines = data.splitlines()
            request_line = request_lines[0]
            headers = {}
            body = None

            # 解析 request_line: "POST /submit HTTP/1.1"
            method, path, version = request_line.split()

            # 解析 headers
            for line in request_lines[1:]:
                if line == '':
                    # 空行代表 headers 結束，下面是 body
                    body_start_index = request_lines.index(line) + 1
                    body = '\r\n'.join(request_lines[body_start_index:])
                    break
                header_key, header_value = line.split(": ", 1)
                headers[header_key] = header_value

            if data:
                request_line = data.decode().split('\r\n')[0]
                print(f"Received request: {request_line}")

                handler = self.routes[path]

                if inspect.isgeneratorfunction(handler):
                    response = yield from handler(headers, body)
                else:
                    response = handler(headers, body)

                response_lines = self.routes[path]()

                response = (
                    "HTTP/1.1 200 OK\r\n"
                    "Content-Type: text/plain\r\n"
                    f"Content-Length: {len(response_lines)}\r\n"
                    "\r\n" +
                    response_lines
                )
                client_socket.sendall(response.encode())
            else:
                logger.debug("Closing connection")
                selector.unregister(client_socket)
                client_socket.close()
        except ConnectionResetError:
            logger.debug("Connection reset by peer")
            selector.unregister(client_socket)
            client_socket.close()
        except BrokenPipeError:
            logger.debug("Broken pipe error")
            selector.unregister(client_socket)
            client_socket.close()

    def serve(self, host, port):
        selector = selectors.DefaultSelector()
        server_socket = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
        server_socket.bind((host, port))
        server_socket.listen()
        server_socket.setblocking(False)
        selector.register(server_socket, selectors.EVENT_READ, data=None)

        self._selector = selector
        self._server_socket = server_socket
        return self.server_step()

    def server_step(self):
        try:
            self.running = True
            while self.running:
                events = self._selector.select()
                for key, _ in events:
                    print(f"SelectorKey: {key}")
                    if key.data is None:
                        self.accept_connection(key.fileobj)
                    else:
                        yield from self.handle_client(key.fileobj)
                yield from Future.done()
        except KeyboardInterrupt:
            print("Server stopped by user")
        finally:
            self._selector.close()
            self._server_socket.close()

    def get(self, path):
        def decorator(func):
            self.routes[path] = func
            return func

        return decorator
