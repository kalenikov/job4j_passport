create table passport
(
    id      serial primary key,
    series  varchar(8),
    created timestamp without time zone not null default now()
);