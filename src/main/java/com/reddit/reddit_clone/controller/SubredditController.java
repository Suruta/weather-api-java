package com.reddit.reddit_clone.controller;

import java.util.List;

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

import com.reddit.reddit_clone.dto.SubredditRequest;
import com.reddit.reddit_clone.dto.SubredditResponse;
import com.reddit.reddit_clone.service.SubredditService;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/api/v1/subreddits")
@AllArgsConstructor
public class SubredditController {
	private final SubredditService subredditService;

	@PostMapping
	public ResponseEntity<String> create(@RequestBody SubredditRequest subredditRequest) {
		subredditService.create(subredditRequest);
		return new ResponseEntity<>("Subreddit created successfully", HttpStatus.CREATED);
	}

	@GetMapping
	public List<SubredditResponse> getAll() {
		return subredditService.getAll();
	}

	@GetMapping("/{id}")
	public SubredditResponse getById(@PathVariable Long id) {
		return subredditService.getById(id);
	}

	@PutMapping("/{id}")
	public ResponseEntity<String> update(@PathVariable Long id, @RequestBody SubredditRequest subredditRequest) {
		subredditService.update(id, subredditRequest);
		return new ResponseEntity<>("Subreddit updated successfully", HttpStatus.OK);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<String> delete(@PathVariable Long id) {
		subredditService.delete(id);
		return new ResponseEntity<>("Subreddit deleted successfully", HttpStatus.OK);
	}
}








