package com.reddit.reddit_clone.comment;

import java.time.Instant;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CommentResponse {
	private Long commentId;
	private String text;

	private Long postId;
	private String postName;

	private Instant createdDate;

	private Long userId;
	private String username;
}