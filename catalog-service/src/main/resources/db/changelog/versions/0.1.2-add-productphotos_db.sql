create sequence photo_seq start with 1 increment by 1;

create table photos (
    id bigint primary key default nextval('photo_seq'),
    product_id bigint references products(id) on DELETE cascade ,
    photoLink varchar
)