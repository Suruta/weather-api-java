package com.reddit.reddit_clone.model;

import static org.junit.jupiter.api.Assertions.*;

import com.reddit.reddit_clone.model.Post.PostType;
import java.time.Instant;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

class PostTest {

    private static final Instant CREATED = Instant.parse("2026-01-01T00:00:00Z");

    private Post post;

    @BeforeEach
    void setUp() {
        post = new Post("p-1", "author-1", "Hello", "World", PostType.TEXT, CREATED);
    }

    // ---- construction ----

    @Test
    void simpleConstructor_generatesIdAndTimestamp() {
        Instant before = Instant.now();
        Post created = new Post("author-1", "Title", "Body", PostType.LINK);
        Instant after = Instant.now();

        assertNotNull(created.getId());
        assertFalse(created.getId().isBlank());
        assertEquals("author-1", created.getAuthorId());
        assertEquals("Title", created.getTitle());
        assertEquals("Body", created.getContent());
        assertEquals(PostType.LINK, created.getType());
        assertFalse(created.getCreatedAt().isBefore(before));
        assertFalse(created.getCreatedAt().isAfter(after));
    }

    @Test
    void simpleConstructor_generatesDistinctIds() {
        Post a = new Post("author-1", "Title", "Body", PostType.TEXT);
        Post b = new Post("author-1", "Title", "Body", PostType.TEXT);

        assertNotEquals(a.getId(), b.getId());
    }

    @Test
    void fullConstructor_setsAllFields() {
        assertEquals("p-1", post.getId());
        assertEquals("author-1", post.getAuthorId());
        assertEquals("Hello", post.getTitle());
        assertEquals("World", post.getContent());
        assertEquals(PostType.TEXT, post.getType());
        assertEquals(CREATED, post.getCreatedAt());
    }

    @Test
    void newPost_hasNeutralInitialState() {
        assertEquals(0, post.getScore());
        assertEquals(0, post.getShareCount());
        assertNull(post.getSubredditId());
    }

    @ParameterizedTest
    @EnumSource(PostType.class)
    void constructor_acceptsEveryPostType(PostType type) {
        Post p = new Post("p-x", "author-1", "Title", "Body", type, CREATED);

        assertEquals(type, p.getType());
    }

    @Test
    void postType_hasExpectedValues() {
        assertArrayEquals(
                new PostType[] {PostType.TEXT, PostType.LINK, PostType.IMAGE, PostType.VIDEO},
                PostType.values());
    }

    @Test
    void constructor_rejectsNullId() {
        assertThrows(NullPointerException.class,
                () -> new Post(null, "author-1", "T", "C", PostType.TEXT, CREATED));
    }

    @Test
    void constructor_rejectsNullAuthorId() {
        assertThrows(NullPointerException.class,
                () -> new Post("p-1", null, "T", "C", PostType.TEXT, CREATED));
    }

    @Test
    void constructor_rejectsNullContent() {
        assertThrows(NullPointerException.class,
                () -> new Post("p-1", "author-1", "T", null, PostType.TEXT, CREATED));
    }

    @Test
    void constructor_allowsEmptyContent() {
        Post p = new Post("p-1", "author-1", "T", "", PostType.TEXT, CREATED);

        assertEquals("", p.getContent());
    }

    @Test
    void constructor_rejectsNullType() {
        assertThrows(NullPointerException.class,
                () -> new Post("p-1", "author-1", "T", "C", null, CREATED));
    }

    @Test
    void constructor_rejectsNullCreatedAt() {
        assertThrows(NullPointerException.class,
                () -> new Post("p-1", "author-1", "T", "C", PostType.TEXT, null));
    }

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {" ", "   ", "\t", "\n"})
    void constructor_rejectsBlankTitle(String badTitle) {
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                () -> new Post("p-1", "author-1", badTitle, "C", PostType.TEXT, CREATED));
        assertEquals("title must not be blank", ex.getMessage());
    }

    // ---- setters ----

    @Test
    void setTitle_updatesValue() {
        post.setTitle("New title");

        assertEquals("New title", post.getTitle());
    }

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {" ", "\t"})
    void setTitle_rejectsBlank_andKeepsOldValue(String badTitle) {
        assertThrows(IllegalArgumentException.class, () -> post.setTitle(badTitle));

        assertEquals("Hello", post.getTitle());
    }

    @Test
    void setContent_updatesValue() {
        post.setContent("Edited");

        assertEquals("Edited", post.getContent());
    }

    @Test
    void setContent_allowsEmpty() {
        post.setContent("");

        assertEquals("", post.getContent());
    }

    @Test
    void setContent_rejectsNull_andKeepsOldValue() {
        assertThrows(NullPointerException.class, () -> post.setContent(null));

        assertEquals("World", post.getContent());
    }

    @Test
    void setSubredditId_assignsAndAllowsClearing() {
        post.setSubredditId("s-1");
        assertEquals("s-1", post.getSubredditId());

        post.setSubredditId(null);
        assertNull(post.getSubredditId());
    }

    // ---- voting ----

    @Test
    void upvote_increasesScore() {
        post.upvote("u-1");

        assertEquals(1, post.getScore());
    }

    @Test
    void downvote_decreasesScore() {
        post.downvote("u-1");

        assertEquals(-1, post.getScore());
    }

    @Test
    void upvote_sameUserTwiceCountsOnce() {
        post.upvote("u-1");
        post.upvote("u-1");

        assertEquals(1, post.getScore());
    }

    @Test
    void downvote_sameUserTwiceCountsOnce() {
        post.downvote("u-1");
        post.downvote("u-1");

        assertEquals(-1, post.getScore());
    }

    @Test
    void upvote_afterDownvote_flipsVote() {
        post.downvote("u-1");
        post.upvote("u-1");

        assertEquals(1, post.getScore());
    }

    @Test
    void downvote_afterUpvote_flipsVote() {
        post.upvote("u-1");
        post.downvote("u-1");

        assertEquals(-1, post.getScore());
    }

    @Test
    void score_sumsVotesFromDifferentUsers() {
        post.upvote("u-1");
        post.upvote("u-2");
        post.upvote("u-3");
        post.downvote("u-4");

        assertEquals(2, post.getScore());
    }

    @Test
    void score_canBalanceToZero() {
        post.upvote("u-1");
        post.downvote("u-2");

        assertEquals(0, post.getScore());
    }

    @Test
    void votes_fromDifferentUsersDoNotInterfere() {
        post.upvote("u-1");
        post.downvote("u-2");
        post.downvote("u-1"); // u-1 flips, u-2 unchanged

        assertEquals(-2, post.getScore());
    }

    // ---- sharing ----

    @Test
    void share_incrementsCounter() {
        post.share();
        post.share();
        post.share();

        assertEquals(3, post.getShareCount());
    }

    @Test
    void share_doesNotAffectScore() {
        post.share();

        assertEquals(0, post.getScore());
    }

    // ---- equals / hashCode / toString ----

    @Test
    void equals_isBasedOnIdOnly() {
        Post sameId = new Post("p-1", "other-author", "Other", "Other", PostType.VIDEO, Instant.now());
        Post otherId = new Post("p-2", "author-1", "Hello", "World", PostType.TEXT, CREATED);

        assertEquals(post, sameId);
        assertEquals(post.hashCode(), sameId.hashCode());
        assertNotEquals(post, otherId);
    }

    @Test
    void equals_handlesSelfNullAndOtherTypes() {
        assertEquals(post, post);
        assertNotEquals(null, post);
        assertNotEquals("p-1", post);
    }

    @Test
    void toString_showsIdTitleTypeAndScore() {
        post.upvote("u-1");

        assertEquals("Post{id='p-1', title='Hello', type=TEXT, score=1}", post.toString());
    }
}