package com.reddit.model;

import java.time.Instant;
import java.util.Objects;
import java.util.UUID;

/**
 * A registered user of the platform.
 *
 * A user can be a regular member or a privileged (admin) member.
 * Kept intentionally simple for Week 1 — no auth/password handling yet,
 * that will come with the API/persistence layer (see RC-12 in the backlog).
 */
public class User {

    private final String id;
    private String username;
    private String email;
    private boolean admin;
    private final Instant createdAt;

    public User(String username, String email) {
        this(UUID.randomUUID().toString(), username, email, false, Instant.now());
    }

    public User(String id, String username, String email, boolean admin, Instant createdAt) {
        this.id = Objects.requireNonNull(id, "id must not be null");
        this.username = requireNonBlank(username, "username");
        this.email = requireNonBlank(email, "email");
        this.admin = admin;
        this.createdAt = Objects.requireNonNull(createdAt, "createdAt must not be null");
    }

    private static String requireNonBlank(String value, String fieldName) {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException(fieldName + " must not be blank");
        }
        return value;
    }

    public String getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = requireNonBlank(username, "username");
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = requireNonBlank(email, "email");
    }

    public boolean isAdmin() {
        return admin;
    }

    public void setAdmin(boolean admin) {
        this.admin = admin;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User user)) return false;
        return id.equals(user.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "User{id='%s', username='%s', admin=%s}".formatted(id, username, admin);
    }
}
