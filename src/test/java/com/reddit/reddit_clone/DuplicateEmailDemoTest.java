package com.reddit.reddit_clone;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.core.JdbcTemplate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class DuplicateEmailDemoTest extends PostgresIntegrationTest {

    @Autowired
    private DuplicateEmailDemo demo;

    @Autowired
    private UserJdbc users;

    @Autowired
    private JdbcTemplate jdbc;

    @BeforeEach
    void clean() {
        jdbc.update("DELETE FROM users");
    }

    @Test
    void secondInsertFailsAndFirstIsRolledBack() {
        assertThatThrownBy(() -> demo.insertTwoAlices())
            .isInstanceOf(DataIntegrityViolationException.class);

        assertThat(users.countByEmail("alice@example.com")).isZero();
    }
}