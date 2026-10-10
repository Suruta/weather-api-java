package com.reddit.reddit_clone;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Component
public class DuplicateEmailDemo {

    private final UserJdbc users;

    public DuplicateEmailDemo(UserJdbc users) {
        this.users = users;
    }

    @Transactional
    public void insertTwoAlices() {
        users.insert(UUID.fromString("11111111-1111-1111-1111-111111111111"),
                "alice", "alice@example.com", false);

        // Fails: email already exists. The exception rolls back the first insert.
        users.insert(UUID.fromString("22222222-2222-2222-2222-222222222222"),
                "alice2", "alice@example.com", false);
    }
}