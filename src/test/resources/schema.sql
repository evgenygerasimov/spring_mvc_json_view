CREATE TABLE IF NOT EXISTS users (id SERIAL PRIMARY KEY, name VARCHAR(255), email VARCHAR(255));

CREATE TABLE IF NOT EXISTS orders (id SERIAL PRIMARY KEY, amount int, product VARCHAR(255),
    status VARCHAR(255), user_id int, FOREIGN KEY (user_id) REFERENCES users(id));