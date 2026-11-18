-- SET lock_timeout = "5s";

ALTER TABLE users ADD UNIQUE (username);
ALTER TABLE users ADD UNIQUE (email);
ALTER TABLE subreddits ADD UNIQUE (name);
ALTER TABLE posts ADD UNIQUE (post_name);
ALTER TABLE tokens ADD UNIQUE (token);