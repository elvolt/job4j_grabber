CREATE TABLE post
(
    id      serial PRIMARY KEY,
    name    varchar(300),
    text    TEXT,
    link    varchar(300) UNIQUE,
    created timestamp
)