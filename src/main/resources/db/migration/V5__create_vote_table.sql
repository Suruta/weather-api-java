CREATE TABLE votes (
    vote_id BIGSERIAL PRIMARY KEY,
    vote_type VARCHAR(255),
    post_id BIGINT,
    user_id BIGINT,
    CONSTRAINT fk_votes_post FOREIGN KEY (post_id) REFERENCES posts(post_id),
    CONSTRAINT fk_votes_user FOREIGN KEY (user_id) REFERENCES users(user_id)
);