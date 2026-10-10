package com.reddit.reddit_clone;

import org.springframework.boot.CommandLineRunner;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.nio.file.Files;
import java.nio.file.Path;

@Component
public class DatabaseInitializer implements CommandLineRunner {

    private final JdbcTemplate jdbc;
    private final DuplicateEmailDemo demo;
    private final UserJdbc users;

    public DatabaseInitializer(JdbcTemplate jdbc, DuplicateEmailDemo demo, UserJdbc users) {
        this.jdbc = jdbc;
        this.demo = demo;
        this.users = users;
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

        System.out.println("Rows with alice@example.com: " + users.countByEmail("alice@example.com"));
    }
}