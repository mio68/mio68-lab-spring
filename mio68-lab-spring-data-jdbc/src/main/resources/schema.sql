CREATE TABLE resource (
    id serial PRIMARY KEY,
    code varchar,
    name varchar,
    description varchar,
    type varchar);

CREATE TABLE times (
    id serial PRIMARY KEY,
    moment timestamp);