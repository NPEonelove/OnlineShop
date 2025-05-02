CREATE SEQUENCE product_seq START WITH 1 INCREMENT BY 1;
CREATE SEQUENCE category_seq START WITH 1 INCREMENT BY 1;

create table if not exists products (
    id bigint primary key default nextval('product_seq') ,
    name varchar(255) unique not null ,
    description varchar(2000) ,
    price numeric(10,2) not null check ( price > 0 ),
    photo varchar,
    count int check ( count >= 0 ) not null ,
    rating numeric(1, 2) default 0
);

create table if not exists categories (
    id bigint primary key default nextval('category_seq') ,
    name varchar(255) unique not null
);

create table if not exists product_category (
    product_id bigint references products(id) on delete set null ,
    category_id bigint references categories(id) on delete set null ,
    primary key (product_id, category_id)
);