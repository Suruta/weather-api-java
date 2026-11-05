CREATE TABLE tokens (
    token_id BIGSERIAL PRIMARY KEY,
    token VARCHAR(255) NOT NULL,
    expiry_date TIMESTAMP,
    user_id BIGINT,
    -- "user" is a reserved keyword in PostgreSQL, so it must be quoted
    CONSTRAINT fk_tokens_user FOREIGN KEY (user_id) REFERENCES users(user_id)
);
