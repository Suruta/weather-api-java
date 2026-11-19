package com.reddit.reddit_clone.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.reddit.reddit_clone.dto.PostRequest;
import com.reddit.reddit_clone.dto.PostResponse;
import com.reddit.reddit_clone.service.PostService;
import com.reddit.reddit_clone.model.Post;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/api/v1/posts")
@AllArgsConstructor
public class PostController {
	private final PostService postService;

	@PostMapping
	public ResponseEntity<String> createPost(@RequestBody PostRequest postRequest) {
		postService.createPost(postRequest);
		return new ResponseEntity<>("Post created successfully", HttpStatus.CREATED);
	}

	@GetMapping
	public List<PostResponse> getPosts() {
		return postService.getPosts();
	}

	@GetMapping("/{id}")
	public PostResponse getPostById(@PathVariable Long id) {
		return postService.getPostById(id);
	}

	@PutMapping("/{id}")
	public ResponseEntity<String> updatePost(@PathVariable Long id, @RequestBody PostRequest postRequest) {
		postService.updatePost(id, postRequest);
		return new ResponseEntity<>("Post updated successfully", HttpStatus.OK);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<String> deletePost(@PathVariable Long id) {
		postService.deletePost(id);
		return new ResponseEntity<>("Post deleted successfully", HttpStatus.OK);
	}

	@GetMapping(params = "subredditId")
	public List<PostResponse> getPostsBySubredditId(@RequestParam Long subredditId) {
		return postService.getPostsBySubredditId(subredditId);
	}

	@GetMapping(params = "username")
	public List<PostResponse> getPostsByUsername(@RequestParam String username) {
		return postService.getPostsByUsername(username);
	}
}







