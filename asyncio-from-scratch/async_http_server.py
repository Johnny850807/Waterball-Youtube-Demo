import inspect
import logging
import selectors
import socket

import waterball

logger = logging.getLogger(__name__)


class SimpleAsyncHttpServer:
    def __init__(self):
        self.routes = {}
        self.running = False
        self._server_socket = None

    def accept_connection(self, server_socket):
        client_socket, addr = server_socket.accept()
        logger.info(f"Accepted connection from {addr}")
        client_socket.setblocking(False)
        waterball.register(client_socket, selectors.EVENT_READ, lambda: (self.handle_client(client_socket)), f"Handle client {client_socket.fileno()}")

    def handle_client(self, client_socket):
        try:
            # 簡單實作，暫時不考慮 client 傳遞的資料超過 4096 字元的情況
            data = client_socket.recv(4096)

            request_lines = data.splitlines()
            request_line = request_lines[0]
            print(request_line)
            headers = {}
            body = None

            # 解析 request_line: "POST /submit HTTP/1.1"
            method, path, version = request_line.split()

            # 解析 headers
            for i in range(len(request_lines[1:])):
                line = request_lines[i + 1]
                if len(line) == 0:
                    # 空行代表 headers 結束，緊接著是 body
                    body_start_index = i + 1
                    body = '\n'.join([l.decode('utf-8') for l in request_lines[body_start_index:]])
                    print(body)
                    break
                header_key, header_value = line.decode('utf-8').split(": ", 1)
                headers[header_key] = header_value
            if data:
                handler = self.routes[path.decode('utf-8')]

                # if inspect.isgeneratorfunction(handler):
                #     response_lines = yield from handler()
                # else:
                response_lines = handler()

                response = (
                    "HTTP/1.1 200 OK\n"
                    "Content-Type: text/plain\n"
                    f"Content-Length: {len(response_lines)}\n"
                    "\n" +
                    response_lines
                )

                client_socket.sendall(response.encode())
            else:
                logger.debug("Closing connection")
        except ConnectionResetError:
            logger.debug("Connection reset by peer")
        except BrokenPipeError:
            logger.debug("Broken pipe error")
        finally:
            waterball.unregister(client_socket)
            client_socket.close()

    def serve(self, host, port):
        logger.debug("Starting the server...")
        server_socket = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
        server_socket.bind((host, port))
        server_socket.listen()
        server_socket.setblocking(False)
        waterball.register(server_socket, selectors.EVENT_READ, lambda: self.accept_connection(server_socket), f"Accept Connection")
        logger.info("Server started.")
        self._server_socket = server_socket

    def get(self, path):
        def decorator(func):
            self.routes[path] = func
            return func

        return decorator

    def stop(self):
        self._server_socket.close()
        waterball.unregister(self._server_socket)
        self.running = False
