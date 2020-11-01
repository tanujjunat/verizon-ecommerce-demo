create table IF NOT EXISTS product(
  id SERIAL PRIMARY KEY,
  name  varchar(255),
  description  varchar(255),
  category  varchar(255)
);

create table IF NOT EXISTS items(
  id SERIAL PRIMARY KEY,
  itemid int,
  skuid int,
  productid int,
  quantity int,
  price float
);
