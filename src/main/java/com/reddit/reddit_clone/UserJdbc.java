package com.reddit.reddit_clone;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public class UserJdbc {

    private final JdbcTemplate jdbc;

    public UserJdbc(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    public void insert(UUID id, String username, String email, boolean admin) {
        jdbc.update("""
            INSERT INTO users (id, username, email, admin, created_at)
            VALUES (?, ?, ?, ?, NOW())
            """, id, username, email, admin);
    }

    public int countByEmail(String email) {
        Integer n = jdbc.queryForObject(
            "SELECT count(*) FROM users WHERE email = ?",
            Integer.class,
            email);
        return n == null ? 0 : n;
    }
}