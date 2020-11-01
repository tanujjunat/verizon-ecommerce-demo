CREATE TABLE user (id SERIAL PRIMARY KEY, first_name VARCHAR(255), last_name VARCHAR(255), phone_number int);

CREATE TABLE shipping_address
(id SERIAL PRIMARY KEY, user_id long not null, address_line1 varchar(25), address_line2 varchar(25),
city varchar(20), state varchar(20), country varchar(20), postal_code int, FOREIGN KEY (user_id) REFERENCES user(id) );

insert into user (id, first_name, last_name, phone_number) values (1,'Anmoljeet', 'Kaur',1000);