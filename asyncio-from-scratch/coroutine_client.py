import datetime
import re
import socket
import selectors
import waterball


def get_connected_client_socket():
    client_socket = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
    client_socket.setblocking(False)
    client_socket.connect_ex(('localhost', 8000))
    connected_client_socket_future = waterball.Future()

    def on_connected():
        connected_client_socket_future.set_result(client_socket)
        waterball.unregister(client_socket)

    waterball.register(client_socket, selectors.EVENT_WRITE, on_connected)

    return (yield from connected_client_socket_future)


def get_response_http_message(client_socket):
    response_http_message_future = waterball.Future()
    response_http_message = b""
    is_response_http_message_complete = False
    status_line_and_headers_length = None
    response_body_content_length = None

    def on_response_chunk_received():
        nonlocal response_http_message, is_response_http_message_complete, status_line_and_headers_length, response_body_content_length
        response_http_message += client_socket.recv(1024)

        if response_body_content_length is None and (b"\r\n\r\n" in response_http_message or b"\n\n" in response_http_message):
            [status_line_and_headers, body] = re.split(
                r"\r?\n\r?\n", response_http_message.decode("utf-8"), maxsplit=1)

            status_line_and_headers_length = len(status_line_and_headers)

            headers = (header.split(": ", 1)
                       for header in re.split(r"\r?\n", status_line_and_headers)[1:])

            response_body_content_length = int(
                next(filter(lambda header: header[0].lower() == "content-length", headers))[1])

        is_response_http_message_complete = len(
            response_http_message) >= response_body_content_length + status_line_and_headers_length

        if is_response_http_message_complete:
            waterball.unregister(client_socket)
            response_http_message_future.set_result(
                response_http_message.decode("utf-8"))

    waterball.register(client_socket, selectors.EVENT_READ,
                       on_response_chunk_received)

    return (yield from response_http_message_future)


def do_request():
    client_socket = yield from get_connected_client_socket()

    request_body = "Hi I'm client"
    request_http_message = (
        "POST / HTTP/1.1\r\n"
        "Host: localhost\r\n"
        "Connection: close\r\n"
        f"Content-Length: {len(request_body)}\r\n"
        f"\r\n{request_body}"
    )

    client_socket.sendall(request_http_message.encode("utf-8"))
    return (yield from get_response_http_message(client_socket))


def main():
    started_at = datetime.datetime.now()
    coros = [do_request] * 500
    results = yield from waterball.gather(*[coro() for coro in coros])
    print("Execution time:", datetime.datetime.now() - started_at)


waterball.run(main())
