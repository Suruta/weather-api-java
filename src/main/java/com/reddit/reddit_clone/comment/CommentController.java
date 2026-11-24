package com.reddit.reddit_clone.comment;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/comments")
public class CommentController {
	private final CommentService commentService;

	@PostMapping
	public ResponseEntity<String> createComment(@RequestBody CommentRequest commentRequest) {
		commentService.createComment(commentRequest);
		return new ResponseEntity<>("Comment created successfully", HttpStatus.CREATED);
	}

	@GetMapping(params = "postId")
	public List<CommentResponse> getCommentsForPost(@RequestParam Long postId) {
		return commentService.getCommentsForPost(postId);
	}

	@GetMapping(params = "username")
	public List<CommentResponse> getCommentsByUsername(@RequestParam String username) {
		return commentService.getCommentsByUsername(username);
	}
}














