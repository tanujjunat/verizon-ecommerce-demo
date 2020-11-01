CREATE TABLE IF NOT EXISTS orders (serial_id SERIAL PRIMARY KEY,
					order_id VARCHAR(255), 
					item_id VARCHAR(255), 
					payment_id VARCHAR(255), 
					user_id VARCHAR(255), 
					quantity INTEGER, 
					amount INTEGER,
					inventory_updated BOOLEAN,
					shipping_address_updated BOOLEAN);