create type profileRole as enum ('admin', 'user');

create table profiles (
    id bigint generated by default as identity primary key ,
    email varchar(256) not null ,
    password varchar(32) not null ,
    firstName varchar(256) not null ,
    lastName varchar(256) not null ,
    photoLink varchar(1024) ,
    regTime timestamp not null ,
    role profileRole not null
);