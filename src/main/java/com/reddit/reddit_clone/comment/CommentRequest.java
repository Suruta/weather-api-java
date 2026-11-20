package com.reddit.reddit_clone.comment;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentRequest {
	private String text;
	private Long postId;
	private Long userId;
}