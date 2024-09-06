from concurrent.futures import ThreadPoolExecutor
import re
import socket

server_socket = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
server_socket.bind(('localhost', 8000))
server_socket.listen()


def get_request_http_message(client_socket):
    request_http_message = b""
    is_request_http_message_complete = False
    request_line_and_headers_length = None
    request_body_content_length = None

    while not is_request_http_message_complete:
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

    return request_http_message.decode("utf-8")


def handle_client(client_socket):
    get_request_http_message(client_socket)

    response_body = "Hi, I'm server"
    response_http_message = (
        "HTTP/1.1 200 OK\r\n"
        "Content-Type: text/plain\r\n"
        f"Content-Length: {len(response_body)}\r\n"
        f"\r\n{response_body}"
    )

    client_socket.sendall(response_http_message.encode("utf-8"))
    client_socket.close()


try:
    with ThreadPoolExecutor(max_workers=10) as executor:
        while True:
            client_socket, attr = server_socket.accept()
            executor.submit(handle_client, client_socket)
finally:
    server_socket.close()
