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
import org.springframework.web.bind.annotation.RestController;

import com.reddit.reddit_clone.dto.PostRequest;
import com.reddit.reddit_clone.service.PostService;
import com.reddit.reddit_clone.model.Post;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/api/posts")
@AllArgsConstructor
public class PostController {
	private final PostService postService;

	@PostMapping
	public ResponseEntity<String> create(@RequestBody PostRequest postRequest) {
		postService.create(postRequest);
		return new ResponseEntity<>("Post created successfully", HttpStatus.CREATED);
	}

	@GetMapping
	public List<Post> getAll() {
		return postService.getAll();
	}

	@GetMapping("/{id}")
	public Post getById(@PathVariable Long id) {
		return postService.getById(id);
	}

	@PutMapping("/{id}")
	public ResponseEntity<String> update(@PathVariable Long id, @RequestBody PostRequest postRequest) {
		postService.update(id, postRequest);
		return new ResponseEntity<>("Post updated successfully", HttpStatus.OK);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<String> delete(@PathVariable Long id) {
		postService.delete(id);
		return new ResponseEntity<>("Post deleted successfully", HttpStatus.OK);
	}
}







