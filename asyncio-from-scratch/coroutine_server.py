import re
import socket
import selectors
import waterball

server_socket = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
server_socket.bind(('localhost', 8000))
server_socket.listen()
server_socket.setblocking(False)


def get_request_http_message(client_socket):
    request_http_message_future = waterball.Future()
    request_http_message = b""
    is_request_http_message_complete = False
    request_line_and_headers_length = None
    request_body_content_length = None

    def on_request_chunk_received():
        nonlocal request_http_message, is_request_http_message_complete, request_line_and_headers_length, request_body_content_length

        request_http_message += client_socket.recv(1024)

        if request_body_content_length is None and (b"\r\n\r\n" in request_http_message or b"\n\n" in request_http_message):
            [request_line_and_headers, body] = re.split(
                r"\r?\n\r?\n", request_http_message.decode("utf-8"), maxsplit=1)

            request_line_and_headers_length = len(request_line_and_headers)

            headers = (header.split(": ", 1)
                       for header in re.split(r"\r?\n", request_line_and_headers)[1:])

            request_body_content_length = int(
                next(filter(lambda header: header[0].lower() == "content-length", headers))[1])

        is_request_http_message_complete = len(
            request_http_message) >= request_body_content_length + request_line_and_headers_length

        if is_request_http_message_complete:
            waterball.unregister(client_socket)
            request_http_message_future.set_result(
                request_http_message.decode("utf-8"))

    waterball.register(client_socket, selectors.EVENT_READ,
                       on_request_chunk_received)

    return (yield from request_http_message_future)


def handle_client(client_socket):
    yield from get_request_http_message(client_socket)

    response_body = "Hi, I'm server"
    response_http_message = (
        "HTTP/1.1 200 OK\r\n"
        "Content-Type: text/plain\r\n"
        f"Content-Length: {len(response_body)}\r\n"
        f"\r\n{response_body}"
    )

    client_socket.sendall(response_http_message.encode("utf-8"))
    client_socket.close()


def on_connection(server_socket):
    client_socket, addr = server_socket.accept()
    client_socket.setblocking(False)
    waterball.schedule_task(handle_client(client_socket))


def main():
    try:
        waterball.register(server_socket, selectors.EVENT_READ,
                           lambda: on_connection(server_socket))

        while True:
            yield from waterball.sleep(0)
    finally:
        waterball.unregister(server_socket)
        server_socket.close()


waterball.run(main())
