create table requests_history(
    id int identity,
    type nvarchar(255),
    request_text ntext,
    response_text ntext
)