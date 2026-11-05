CREATE TABLE comments (
    id BIGSERIAL PRIMARY KEY,
    text TEXT NOT NULL,
    created_date TIMESTAMP,
    post_id BIGINT,
    user_id BIGINT,
    CONSTRAINT fk_comments_post FOREIGN KEY (post_id) REFERENCES posts(post_id),
    CONSTRAINT fk_comments_user FOREIGN KEY (user_id) REFERENCES users(user_id)
);