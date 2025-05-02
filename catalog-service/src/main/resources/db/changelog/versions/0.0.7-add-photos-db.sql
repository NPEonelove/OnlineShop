create sequence if not exists productPhotos_seq start with 1 increment 1;

create table if not exists productPhotos (
    id bigint primary key default nextval('productPhotos_seq'),
    product_id bigint references products(id) on delete cascade not null ,
    url text not null
);