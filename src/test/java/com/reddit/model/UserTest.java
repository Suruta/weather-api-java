package com.reddit.model;

import static org.junit.jupiter.api.Assertions.*;

import java.time.Instant;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

class UserTest {

    private static final Instant CREATED = Instant.parse("2026-01-01T00:00:00Z");

    private User user;

    @BeforeEach
    void setUp() {
        user = new User("u-1", "alice", "alice@example.com", false, CREATED);
    }

    @Test
    void simpleConstructor_generatesIdAndTimestamp_andDefaultsToNonAdmin() {
        Instant before = Instant.now();
        User created = new User("bob", "bob@example.com");
        Instant after = Instant.now();

        assertNotNull(created.getId());
        assertFalse(created.getId().isBlank());
        assertEquals("bob", created.getUsername());
        assertEquals("bob@example.com", created.getEmail());
        assertFalse(created.isAdmin());
        assertFalse(created.getCreatedAt().isBefore(before));
        assertFalse(created.getCreatedAt().isAfter(after));
    }

    @Test
    void simpleConstructor_generatesDistinctIds() {
        User a = new User("bob", "bob@example.com");
        User b = new User("bob", "bob@example.com");

        assertNotEquals(a.getId(), b.getId());
    }

    @Test
    void fullConstructor_setsAllFields() {
        User admin = new User("u-2", "root", "root@example.com", true, CREATED);

        assertEquals("u-2", admin.getId());
        assertEquals("root", admin.getUsername());
        assertEquals("root@example.com", admin.getEmail());
        assertTrue(admin.isAdmin());
        assertEquals(CREATED, admin.getCreatedAt());
    }

    @Test
    void fullConstructor_rejectsNullId() {
        NullPointerException ex = assertThrows(NullPointerException.class,
                () -> new User(null, "alice", "alice@example.com", false, CREATED));
        assertEquals("id must not be null", ex.getMessage());
    }

    @Test
    void fullConstructor_rejectsNullCreatedAt() {
        NullPointerException ex = assertThrows(NullPointerException.class,
                () -> new User("u-1", "alice", "alice@example.com", false, null));
        assertEquals("createdAt must not be null", ex.getMessage());
    }

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {" ", "   ", "\t", "\n"})
    void constructor_rejectsBlankUsername(String badUsername) {
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                () -> new User("u-1", badUsername, "alice@example.com", false, CREATED));
        assertEquals("username must not be blank", ex.getMessage());
    }

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {" ", "   ", "\t", "\n"})
    void constructor_rejectsBlankEmail(String badEmail) {
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                () -> new User("u-1", "alice", badEmail, false, CREATED));
        assertEquals("email must not be blank", ex.getMessage());
    }

    @Test
    void setUsername_updatesValue() {
        user.setUsername("alice2");

        assertEquals("alice2", user.getUsername());
    }

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {" ", "\t"})
    void setUsername_rejectsBlank_andKeepsOldValue(String badUsername) {
        assertThrows(IllegalArgumentException.class, () -> user.setUsername(badUsername));

        assertEquals("alice", user.getUsername());
    }

    @Test
    void setEmail_updatesValue() {
        user.setEmail("new@example.com");

        assertEquals("new@example.com", user.getEmail());
    }

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {" ", "\t"})
    void setEmail_rejectsBlank_andKeepsOldValue(String badEmail) {
        assertThrows(IllegalArgumentException.class, () -> user.setEmail(badEmail));

        assertEquals("alice@example.com", user.getEmail());
    }

    @Test
    void setAdmin_togglesFlag() {
        user.setAdmin(true);
        assertTrue(user.isAdmin());

        user.setAdmin(false);
        assertFalse(user.isAdmin());
    }

    @Test
    void equals_isBasedOnIdOnly() {
        User sameId = new User("u-1", "different", "different@example.com", true, Instant.now());
        User otherId = new User("u-2", "alice", "alice@example.com", false, CREATED);

        assertEquals(user, sameId);
        assertEquals(user.hashCode(), sameId.hashCode());
        assertNotEquals(user, otherId);
    }

    @Test
    void equals_handlesSelfNullAndOtherTypes() {
        assertEquals(user, user);
        assertNotEquals(null, user);
        assertNotEquals("u-1", user);
    }

    @Test
    void toString_containsKeyFields_butNotEmail() {
        String text = user.toString();

        assertTrue(text.contains("u-1"));
        assertTrue(text.contains("alice"));
        assertTrue(text.contains("admin=false"));
        assertFalse(text.contains("alice@example.com"));
    }
}