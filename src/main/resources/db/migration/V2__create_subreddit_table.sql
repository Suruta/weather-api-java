CREATE TABLE subreddits (
    subreddit_id BIGSERIAL PRIMARY KEY,
    name VARCHAR(255),
    description TEXT,
    created_date TIMESTAMP,
    user_id BIGINT,
    CONSTRAINT fk_subreddits_user FOREIGN KEY (user_id) REFERENCES users(user_id)
);