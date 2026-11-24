package com.reddit.reddit_clone.post;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostRequest {
	private String postName;
	private String url;
	private String description;
	private Long subredditId;
}