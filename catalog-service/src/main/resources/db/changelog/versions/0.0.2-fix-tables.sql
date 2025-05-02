drop table if exists product_category;

create table if not exists product_category
(
    product_id  bigint references products(id) on delete set null,
    category_id bigint references categories(id) on delete set null,
    primary key (product_id, category_id)
);