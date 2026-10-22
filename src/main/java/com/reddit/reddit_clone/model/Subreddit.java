package com.reddit.reddit_clone.model;

import java.time.Instant;
import java.util.List;

import org.springframework.data.annotation.Id;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
public class Subreddit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String description;

    @OneToMany(fetch = FetchType.LAZY)
    private List<Post> posts;
    private Instant createdDate;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;
}





