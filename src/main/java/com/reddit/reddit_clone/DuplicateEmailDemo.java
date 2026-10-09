package com.reddit.reddit_clone;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Component
public class DuplicateEmailDemo {

    private final JdbcTemplate jdbc;

    public DuplicateEmailDemo(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    @Transactional
    public void insertTwoAlices() {
        jdbc.update(
                "INSERT INTO users (id, username, email, admin, created_at) VALUES (?, ?, ?, ?, NOW())",
                UUID.fromString("11111111-1111-1111-1111-111111111111"),
                "alice", "alice@example.com", false);

        // Fails: email already exists. The exception rolls back the first insert.
        jdbc.update(
                "INSERT INTO users (id, username, email, admin, created_at) VALUES (?, ?, ?, ?, NOW())",
                UUID.fromString("22222222-2222-2222-2222-222222222222"),
                "alice2", "alice@example.com", false);
    }
}