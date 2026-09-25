package com.redditclone.model;

import java.time.Instant;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

/**
 * A post submitted by a {@link User} to a {@link Subreddit}.
 *
 * Supports text, link, image, or video content (kept as a simple enum for now —
 * actual media storage/handling is out of scope for Week 1).
 *
 * Voting and sharing are tracked as simple counters/sets here; a real ranking
 * algorithm (RC-15) will consume this data later.
 */
public class Post {

    public enum PostType { TEXT, LINK, IMAGE, VIDEO }

    private final String id;
    private final String authorId;
    // TODO(RC-8, in progress): wired the field, still need to update constructors/tests
    // consistently and decide whether this should be required or settable post-creation
    // when a post can be cross-posted to multiple subreddits.
    private String subredditId;
    private String title;
    private String content;
    private final PostType type;
    private final Instant createdAt;

    private final Set<String> upvoterIds = new HashSet<>();
    private final Set<String> downvoterIds = new HashSet<>();
    private int shareCount;

    public Post(String authorId, String title, String content, PostType type) {
        this(UUID.randomUUID().toString(), authorId, title, content, type, Instant.now());
    }

    public Post(String id, String authorId, String title, String content, PostType type, Instant createdAt) {
        this.id = Objects.requireNonNull(id, "id must not be null");
        this.authorId = Objects.requireNonNull(authorId, "authorId must not be null");
        this.title = requireNonBlank(title, "title");
        this.content = Objects.requireNonNull(content, "content must not be null");
        this.type = Objects.requireNonNull(type, "type must not be null");
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

    public String getAuthorId() {
        return authorId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = requireNonBlank(title, "title");
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = Objects.requireNonNull(content, "content must not be null");
    }

    public PostType getType() {
        return type;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    /** Which subreddit this post belongs to. Null until assigned (see RC-8 TODO above). */
    public String getSubredditId() {
        return subredditId;
    }

    public void setSubredditId(String subredditId) {
        this.subredditId = subredditId;
    }

    /** Registers an upvote from the given user, clearing any existing downvote from them. */
    public void upvote(String userId) {
        downvoterIds.remove(userId);
        upvoterIds.add(userId);
    }

    /** Registers a downvote from the given user, clearing any existing upvote from them. */
    public void downvote(String userId) {
        upvoterIds.remove(userId);
        downvoterIds.add(userId);
    }

    public int getScore() {
        return upvoterIds.size() - downvoterIds.size();
    }

    public void share() {
        shareCount++;
    }

    public int getShareCount() {
        return shareCount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Post post)) return false;
        return id.equals(post.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Post{id='%s', title='%s', type=%s, score=%d}".formatted(id, title, type, getScore());
    }
}
