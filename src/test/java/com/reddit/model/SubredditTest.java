package com.reddit.model;

import static org.junit.jupiter.api.Assertions.*;

import java.time.Instant;
import java.util.Set;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

class SubredditTest {

    private static final Instant CREATED = Instant.parse("2026-01-01T00:00:00Z");

    private Subreddit subreddit;

    @BeforeEach
    void setUp() {
        subreddit = new Subreddit("s-1", "programming", "All about code", "creator", CREATED);
    }

    // ---- construction ----

    @Test
    void simpleConstructor_generatesIdAndTimestamp() {
        Instant before = Instant.now();
        Subreddit created = new Subreddit("java", "Java talk", "creator");
        Instant after = Instant.now();

        assertNotNull(created.getId());
        assertFalse(created.getId().isBlank());
        assertEquals("java", created.getName());
        assertEquals("Java talk", created.getDescription());
        assertEquals("creator", created.getCreatedByUserId());
        assertFalse(created.getCreatedAt().isBefore(before));
        assertFalse(created.getCreatedAt().isAfter(after));
    }

    @Test
    void fullConstructor_setsAllFields() {
        assertEquals("s-1", subreddit.getId());
        assertEquals("programming", subreddit.getName());
        assertEquals("All about code", subreddit.getDescription());
        assertEquals("creator", subreddit.getCreatedByUserId());
        assertEquals(CREATED, subreddit.getCreatedAt());
    }

    @Test
    void constructor_makesCreatorTheFirstMember() {
        assertEquals(Set.of("creator"), subreddit.getMemberUserIds());
        assertEquals(1, subreddit.getMemberCount());
    }

    @Test
    void constructor_startsWithNoPosts() {
        assertTrue(subreddit.getPostIds().isEmpty());
    }

    @Test
    void constructor_nullDescriptionBecomesEmptyString() {
        Subreddit s = new Subreddit("s-2", "javaland", null, "creator", CREATED);

        assertEquals("", s.getDescription());
    }

    @Test
    void constructor_rejectsNullId() {
        assertThrows(NullPointerException.class,
                () -> new Subreddit(null, "programming", "d", "creator", CREATED));
    }

    @Test
    void constructor_rejectsNullCreator() {
        assertThrows(NullPointerException.class,
                () -> new Subreddit("s-1", "programming", "d", null, CREATED));
    }

    @Test
    void constructor_rejectsNullCreatedAt() {
        assertThrows(NullPointerException.class,
                () -> new Subreddit("s-1", "programming", "d", "creator", null));
    }

    // ---- name validation ----

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {" ", "   ", "\t"})
    void constructor_rejectsBlankName(String badName) {
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                () -> new Subreddit("s-1", badName, "d", "creator", CREATED));
        assertEquals("name must not be blank", ex.getMessage());
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "ab",                      // too short
            "abcdefghijklmnopqrstuv",  // 22 chars, too long
            "r/java",                  // prefix not allowed
            "has space",
            "dash-name",
            "dot.name",
            "emoji😀name",
            "ünïcode"
    })
    void constructor_rejectsInvalidNames(String badName) {
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                () -> new Subreddit("s-1", badName, "d", "creator", CREATED));
        assertTrue(ex.getMessage().contains("3-21"));
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "abc",                     // min length 3
            "abcdefghijklmnopqrstu",   // max length 21
            "UPPER",
            "mixed_Case_123",
            "123",
            "___"
    })
    void constructor_acceptsValidNames(String goodName) {
        Subreddit s = new Subreddit("s-1", goodName, "d", "creator", CREATED);

        assertEquals(goodName, s.getName());
    }

    @Test
    void displayName_hasRSlashPrefix() {
        assertEquals("r/programming", subreddit.getDisplayName());
    }

    // ---- description ----

    @Test
    void setDescription_updatesValue() {
        subreddit.setDescription("New description");

        assertEquals("New description", subreddit.getDescription());
    }

    @Test
    void setDescription_nullBecomesEmptyString() {
        subreddit.setDescription(null);

        assertEquals("", subreddit.getDescription());
    }

    // ---- membership ----

    @Test
    void addMember_addsUserAndIncreasesCount() {
        subreddit.addMember("bob");

        assertTrue(subreddit.getMemberUserIds().contains("bob"));
        assertEquals(2, subreddit.getMemberCount());
    }

    @Test
    void addMember_isIdempotent() {
        subreddit.addMember("bob");
        subreddit.addMember("bob");

        assertEquals(2, subreddit.getMemberCount());
    }

    @Test
    void addMember_creatorAgainDoesNotDuplicate() {
        subreddit.addMember("creator");

        assertEquals(1, subreddit.getMemberCount());
    }

    @Test
    void addMember_rejectsNull() {
        assertThrows(NullPointerException.class, () -> subreddit.addMember(null));
    }

    @Test
    void removeMember_removesExistingMember() {
        subreddit.addMember("bob");

        subreddit.removeMember("bob");

        assertFalse(subreddit.getMemberUserIds().contains("bob"));
        assertEquals(1, subreddit.getMemberCount());
    }

    @Test
    void removeMember_unknownUserIsNoOp() {
        subreddit.removeMember("ghost");

        assertEquals(1, subreddit.getMemberCount());
    }

    @Test
    void removeMember_creatorCannotBeRemoved() {
        IllegalStateException ex = assertThrows(IllegalStateException.class,
                () -> subreddit.removeMember("creator"));

        assertEquals("creator cannot be removed as a member", ex.getMessage());
        assertTrue(subreddit.getMemberUserIds().contains("creator"));
    }

    @Test
    void removeMember_nullThrowsNullPointerException() {
        assertThrows(NullPointerException.class, () -> subreddit.removeMember(null));
    }

    @Test
    void getMemberUserIds_returnsUnmodifiableSnapshot() {
        Set<String> snapshot = subreddit.getMemberUserIds();

        assertThrows(UnsupportedOperationException.class, () -> snapshot.add("hacker"));

        subreddit.addMember("bob");
        assertFalse(snapshot.contains("bob"), "earlier snapshot must not see later changes");
        assertTrue(subreddit.getMemberUserIds().contains("bob"));
    }

    // ---- posts ----

    @Test
    void addPost_registersPostId() {
        subreddit.addPost("p-1");

        assertEquals(Set.of("p-1"), subreddit.getPostIds());
    }

    @Test
    void addPost_isIdempotent() {
        subreddit.addPost("p-1");
        subreddit.addPost("p-1");

        assertEquals(1, subreddit.getPostIds().size());
    }

    @Test
    void addPost_rejectsNull() {
        assertThrows(NullPointerException.class, () -> subreddit.addPost(null));
    }

    @Test
    void getPostIds_returnsUnmodifiableSnapshot() {
        subreddit.addPost("p-1");
        Set<String> snapshot = subreddit.getPostIds();

        assertThrows(UnsupportedOperationException.class, () -> snapshot.add("p-2"));

        subreddit.addPost("p-3");
        assertFalse(snapshot.contains("p-3"));
    }

    // ---- equals / hashCode / toString ----

    @Test
    void equals_isBasedOnIdOnly() {
        Subreddit sameId = new Subreddit("s-1", "different", "x", "someone", Instant.now());
        Subreddit otherId = new Subreddit("s-2", "programming", "All about code", "creator", CREATED);

        assertEquals(subreddit, sameId);
        assertEquals(subreddit.hashCode(), sameId.hashCode());
        assertNotEquals(subreddit, otherId);
    }

    @Test
    void equals_handlesSelfNullAndOtherTypes() {
        assertEquals(subreddit, subreddit);
        assertNotEquals(null, subreddit);
        assertNotEquals("s-1", subreddit);
    }

    @Test
    void toString_showsDisplayNameAndCounts() {
        subreddit.addMember("bob");
        subreddit.addPost("p-1");

        assertEquals("Subreddit{name='r/programming', members=2, posts=1}", subreddit.toString());
    }
}