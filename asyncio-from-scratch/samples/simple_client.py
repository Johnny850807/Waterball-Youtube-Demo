import socket
import re

client_socket = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
client_socket.connect(('localhost', 8000))


def do_request(client_socket):
    request_body = "Hi I'm client"
    request_http_message = (
        "POST / HTTP/1.1\r\n"
        "Host: localhost\r\n"
        "Connection: close\r\n"
        f"Content-Length: {len(request_body)}\r\n"
        f"\r\n{request_body}"
    )

    client_socket.sendall(request_http_message.encode("utf-8"))

    response_http_message = b""
    is_response_http_message_complete = False
    status_line_and_headers_length = None
    response_body_content_length = None

    while not is_response_http_message_complete:
        response_http_message += client_socket.recv(1024)

        if response_body_content_length is None and (b"\r\n\r\n" in response_http_message or b"\n\n" in response_http_message):
            [status_line_and_headers, body] = re.split(
                r"\r?\n\r?\n", response_http_message.decode("utf-8"), maxsplit=1)

            status_line_and_headers_length = len(status_line_and_headers)

            headers = (header.split(": ", 1)
                       for header in re.split(r"\r?\n", status_line_and_headers)[1:])

            response_body_content_length = int(
                next(filter(lambda header: header[0].lower() == "content-length", headers))[1])

        body_content_length_received = len(
            response_http_message) - status_line_and_headers_length

        is_response_http_message_complete = body_content_length_received >= response_body_content_length

    print(response_http_message.decode("utf-8"))


do_request(client_socket)
