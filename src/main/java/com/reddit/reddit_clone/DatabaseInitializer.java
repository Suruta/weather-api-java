package com.reddit.reddit_clone;

import org.springframework.boot.CommandLineRunner;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.UUID;

@Component
public class DatabaseInitializer implements CommandLineRunner {

    private final JdbcTemplate jdbc;
    private final DuplicateEmailDemo demo;

    public DatabaseInitializer(JdbcTemplate jdbc, DuplicateEmailDemo demo) {
        this.jdbc = jdbc;
        this.demo = demo;
    }

    @Override
    public void run(String... args) throws Exception {
        jdbc.execute("DROP TABLE IF EXISTS users");
        jdbc.execute(Files.readString(Path.of("src/main/resources/db/schema.sql")));

        try {
            demo.insertTwoAlices();
        } catch (DataAccessException e) {
            System.out.println("Second insert failed, transaction rolled back: "
                    + e.getMostSpecificCause().getMessage());
        }

        Integer count = jdbc.queryForObject(
                "SELECT COUNT(*) FROM users WHERE email = ?", Integer.class, "alice@example.com");
        System.out.println("Rows with alice@example.com: " + count);
    }
}