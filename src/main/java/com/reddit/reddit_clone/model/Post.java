package com.reddit.reddit_clone.model;

import java.time.Instant;

import org.springframework.data.annotation.Id;

import io.micrometer.common.lang.Nullable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long postId;
    private String postName;
    private String url;

    @Lob
    private String description;
    private Integer voteCount = 0;

    @ManyToOne(fetch = LAZY)
    @joinColumn(name = "userid", referencedColumnName = "userid")
    private User user;
    private Instant createdDate;

    @ManyToOne(fetch = LAZY)
    @joinColumn(name = "id", referencedColumnName = "id")
    private Subreddit subreddit;
}








