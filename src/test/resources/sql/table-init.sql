CREATE SCHEMA IF NOT EXISTS test;

create table if not exists test.users(
    id serial primary key,
    email varchar,
    password varchar,
    role_enum_list varchar[],
    permission_enum_list varchar[]
);

create table if not exists test.laptop_entity(
    id serial primary key,
    CPU varchar,
    RAM integer,
    name varchar,
    price double precision
);