CREATE TABLE users (
    id UUID PRIMARY KEY,
    username VARCHAR(150) NOT NULL,
    email VARCHAR(140) NOT NULL UNIQUE,
    admin BOOLEAN NOT NULL DEFAULT FALSE,
    created_at TIMESTAMPTZ NOT NULL
);

INSERT INTO users (id, username, email, admin, created_at)
VALUES
    ('550e8400-e29b-41d4-a716-446655440001', 'alice', 'alice@example.com', TRUE, NOW()),
    ('550e8400-e29b-41d4-a716-446655440002', 'bob', 'bob@example.com', FALSE, NOW()),
    ('550e8400-e29b-41d4-a716-446655440003', 'charlie', 'charlie@example.com', FALSE, NOW()),
    ('550e8400-e29b-41d4-a716-446655440004', 'diana', 'diana@example.com', FALSE, NOW()),
    ('550e8400-e29b-41d4-a716-446655440005', 'eve', 'eve@example.com', FALSE, NOW());

-- BEGIN;

-- INSERT INTO users (id, username, email, admin, created_at)
-- VALUES (
--     '11111111-1111-1111-1111-111111111111',
--     'alice',
--     'alice@example.com',
--     FALSE,
--     NOW()
-- );

-- INSERT INTO users (id, username, email, admin, created_at)
-- VALUES (
--     '22222222-2222-2222-2222-222222222222',
--     'alice2',
--     'alice@example.com',
--     FALSE,
--     NOW()
-- );

-- -- ERROR: duplicate key value violates unique constraint on email

-- ROLLBACK;

-- SELECT COUNT(*) FROM users WHERE email = 'alice@example.com';


-- CREATE TABLE posts (
--     id UUID PRIMARY KEY,
--     author_id UUID NOT NULL REFERENCES users(id),
--     subreddit_id UUID REFERENCES subreddits(id),
--     title VARCHAR(300) NOT NULL,
--     content TEXT NOT NULL,
--     type VARCHAR(10) NOT NULL
--         CHECK (type IN ('TEXT', 'LINK', 'IMAGE', 'VIDEO')),
--     created_at TIMESTAMPTZ NOT NULL,
--     share_count INT NOT NULL DEFAULT 0
--         CHECK (share_count >= 0)
-- );

-- CREATE TABLE subreddits (
--     id UUID PRIMARY KEY,
--     name VARCHAR(21) NOT NULL UNIQUE
--         CHECK (name ~ '^[A-Za-z0-9_]{3,21}$'),
--     description TEXT NOT NULL DEFAULT '',
--     created_by_user_id UUID NOT NULL REFERENCES users(id),
--     created_at TIMESTAMPTZ NOT NULL
-- );