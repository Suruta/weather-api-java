package com.reddit.reddit_clone;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class UserJdbcTest extends PostgresIntegrationTest {

    @Autowired
    private UserJdbc users;

    @Autowired
    private JdbcTemplate jdbc;

    @BeforeEach
    void clean() {
        jdbc.update("DELETE FROM users");
    }

    @Test
    void insertedUserCanBeCountedByEmail() {
        users.insert(UUID.randomUUID(), "bob", "bob@example.com", false);

        assertThat(users.countByEmail("bob@example.com")).isEqualTo(1);
    }

    @Test
    void countIsZeroForUnknownEmail() {
        assertThat(users.countByEmail("nobody@example.com")).isZero();
    }

    @Test
    void duplicateEmailIsRejected() {
        users.insert(UUID.randomUUID(), "carol", "carol@example.com", false);

        assertThatThrownBy(() ->
                users.insert(UUID.randomUUID(), "carol2", "carol@example.com", false))
            .isInstanceOf(DataIntegrityViolationException.class);
    }
}