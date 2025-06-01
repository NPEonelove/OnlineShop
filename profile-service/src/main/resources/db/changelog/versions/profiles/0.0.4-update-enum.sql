alter table profiles drop column role;
alter table profiles
add column role varchar(32) check ( role in ('ADMIN', 'USER') );