package com.reddit.reddit_clone.model;

import java.time.Instant;
import java.util.List;

import org.springframework.data.annotation.Id;

import jakarta.validation.constraints.NotBlank;
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
    @GeneratedValue(strategy = IDENTITY)
    private Long id;
    private String name;
    private String description;

    @OneToMany(fetch = LAZY)
    private List<Post> posts;
    private Instant createdDate;

    @ManyToOne(fetch = LAZY)
    private User user;
}





