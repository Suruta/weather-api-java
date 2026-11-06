package com.reddit.reddit_clone.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SubredditRequest {
	private String name;
	private String description;
	private Long userId;
}