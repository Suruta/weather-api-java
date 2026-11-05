CREATE TABLE users (
    user_id BIGSERIAL PRIMARY KEY,
    username VARCHAR(255),
    password VARCHAR(255),
    email VARCHAR(255),
    created TIMESTAMP,
    enabled BOOLEAN
);