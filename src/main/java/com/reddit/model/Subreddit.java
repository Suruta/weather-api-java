package com.reddit.model;

import java.time.Instant;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

/**
 * A discussion section — "r/{name}" — that groups posts around a topic.
 *
 * Holds membership and the set of posts belonging to it. Content moderation
 * and per-subreddit privileges are out of scope for Week 1 (see backlog).
 */
public class Subreddit {

    private final String id;
    private final String name;
    private String description;
    private final String createdByUserId;
    private final Instant createdAt;

    private final Set<String> memberUserIds = new LinkedHashSet<>();
    private final Set<String> postIds = new LinkedHashSet<>();

    public Subreddit(String name, String description, String createdByUserId) {
        this(UUID.randomUUID().toString(), name, description, createdByUserId, Instant.now());
    }

    public Subreddit(String id, String name, String description, String createdByUserId, Instant createdAt) {
        this.id = Objects.requireNonNull(id, "id must not be null");
        this.name = validateName(name);
        this.description = description == null ? "" : description;
        this.createdByUserId = Objects.requireNonNull(createdByUserId, "createdByUserId must not be null");
        this.createdAt = Objects.requireNonNull(createdAt, "createdAt must not be null");
        this.memberUserIds.add(createdByUserId);
    }

    private static String validateName(String name) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("name must not be blank");
        }
        if (!name.matches("[A-Za-z0-9_]{3,21}")) {
            throw new IllegalArgumentException(
                    "name must be 3-21 chars of letters, digits, or underscore (no 'r/' prefix)");
        }
        return name;
    }

    public String getId() {
        return id;
    }

    /** Topic name without the "r/" prefix, e.g. "programming". */
    public String getName() {
        return name;
    }

    /** Display form, e.g. "r/programming". */
    public String getDisplayName() {
        return "r/" + name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description == null ? "" : description;
    }

    public String getCreatedByUserId() {
        return createdByUserId;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void addMember(String userId) {
        memberUserIds.add(Objects.requireNonNull(userId));
    }

    public void removeMember(String userId) {
        if (userId.equals(createdByUserId)) {
            throw new IllegalStateException("creator cannot be removed as a member");
        }
        memberUserIds.remove(userId);
    }

    public Set<String> getMemberUserIds() {
        return Set.copyOf(memberUserIds);
    }

    public int getMemberCount() {
        return memberUserIds.size();
    }

    public void addPost(String postId) {
        postIds.add(Objects.requireNonNull(postId));
    }

    public Set<String> getPostIds() {
        return Set.copyOf(postIds);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Subreddit subreddit)) return false;
        return id.equals(subreddit.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Subreddit{name='%s', members=%d, posts=%d}".formatted(getDisplayName(), getMemberCount(), postIds.size());
    }
}
