package com.reddit.reddit_clone;

import org.junit.jupiter.api.Test;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.core.JdbcTemplate;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

class DatabaseInitializerTest {

    @Test
    void runsDropSchemaDemoAndCountInOrder() throws Exception {
        JdbcTemplate jdbc = mock(JdbcTemplate.class);
        DuplicateEmailDemo demo = mock(DuplicateEmailDemo.class);
        UserJdbc users = mock(UserJdbc.class);
        when(users.countByEmail("alice@example.com")).thenReturn(0);

        new DatabaseInitializer(jdbc, demo, users).run();

        var order = inOrder(jdbc, demo, users);
        order.verify(jdbc).execute("DROP TABLE IF EXISTS users");
        order.verify(jdbc).execute(anyString());          // schema.sql
        order.verify(demo).insertTwoAlices();
        order.verify(users).countByEmail("alice@example.com");
    }

    @Test
    void continuesWhenDemoThrowsDataAccessException() throws Exception {
        JdbcTemplate jdbc = mock(JdbcTemplate.class);
        DuplicateEmailDemo demo = mock(DuplicateEmailDemo.class);
        UserJdbc users = mock(UserJdbc.class);
        doThrow(new DataIntegrityViolationException("duplicate"))
            .when(demo).insertTwoAlices();
        when(users.countByEmail(any())).thenReturn(0);

        new DatabaseInitializer(jdbc, demo, users).run();

        verify(users).countByEmail("alice@example.com");
    }
}