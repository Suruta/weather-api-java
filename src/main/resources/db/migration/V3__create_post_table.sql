CREATE TABLE posts (
    post_id BIGSERIAL PRIMARY KEY,
    post_name VARCHAR(255),
    url VARCHAR(255),
    description TEXT,
    vote_count INTEGER DEFAULT 0,
    user_id BIGINT,
    created_date TIMESTAMP,
    subreddit_id BIGINT,
    CONSTRAINT fk_posts_user FOREIGN KEY (user_id) REFERENCES users(user_id),
    CONSTRAINT fk_posts_subreddit FOREIGN KEY (subreddit_id) REFERENCES subreddits(subreddit_id)
);