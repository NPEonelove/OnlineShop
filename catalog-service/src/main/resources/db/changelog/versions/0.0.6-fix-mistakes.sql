drop table if exists product_category;
drop table if exists products;
drop table if exists categories;
drop sequence if exists category_seq;
drop sequence if exists product_seq;

CREATE SEQUENCE product_seq START WITH 1 INCREMENT BY 1;
CREATE SEQUENCE category_seq START WITH 1 INCREMENT BY 1;

create table if not exists products
(
    id          bigint primary key default nextval('product_seq'),
    name        varchar(255) unique      not null,
    description varchar(2000),
    price       numeric(10, 2)           not null check ( price > 0 ),
    photo       varchar,
    count       int check ( count >= 0 ) not null,
    rating      numeric(1, 2)      default 0
);

create table if not exists categories
(
    id   bigint primary key default nextval('category_seq'),
    name varchar(255) unique not null
);

create table if not exists product_category
(
    product_id  bigint references products (id) on delete cascade,
    category_id bigint references categories (id) on delete cascade,
    primary key (product_id, category_id)
);

