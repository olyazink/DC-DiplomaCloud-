
create table users
(
    login    varchar(30) not null unique,
    password varchar(80) not null,
    primary key (login)
);

create table tokens
(
    token varchar(100)
);

create table files
(
    id serial,
    file_name      varchar(255) not null,
    file_holder  bytea,
    size          bigint,
    primary key (file_name)
);


insert into public.users (login, password)
values ('user', 'pass'),
       ('user2', 'pass');
