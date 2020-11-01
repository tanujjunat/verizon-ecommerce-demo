create table product(
  id SERIAL PRIMARY KEY,
  name  varchar(255),
  description  varchar(255),
  price float,
  quantity int,
  category  varchar(255)
);

create table items(
  id SERIAL PRIMARY KEY,
  itemid int,
  skuid int,
  productid int,
  quantity int,
  price float
);