CREATE TABLE IF NOT EXISTS payment_response (serial_id SERIAL PRIMARY KEY, 
					payment_id VARCHAR(255), 
					types VARCHAR(255), 
					amount INTEGER);