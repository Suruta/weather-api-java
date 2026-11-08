package com.reddit.reddit_clone.dto;

import java.time.Instant;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SubredditResponse {
	private Long subredditId;
	
	private String name;
	private String description;

	private Long userId;
	private Instant createdDate;
}