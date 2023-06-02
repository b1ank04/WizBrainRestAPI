create table requests_history(
    id SERIAL,
    type varchar,
    request_text text,
    response_text text
)