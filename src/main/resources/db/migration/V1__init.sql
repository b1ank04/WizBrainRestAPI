create table requests_history(
    id SERIAL,
    type varchar(255),
    request_text text,
    response_text text
)