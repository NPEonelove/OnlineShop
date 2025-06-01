alter table products
    add column reviews_count int check ( reviews_count > 0 )