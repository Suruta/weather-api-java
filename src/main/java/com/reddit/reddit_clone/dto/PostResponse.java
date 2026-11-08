package com.reddit.reddit_clone.dto;

import java.time.Instant;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PostResponse {
	private Long postId;
	private String postName;

	private String url;
	private String description;
	private Integer voteCount;

	private Long userId;
	private String username;

	private Long subredditId;
	private String subredditName;

	private Instant createdDate;
}