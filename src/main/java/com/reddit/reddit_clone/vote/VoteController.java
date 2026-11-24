package com.reddit.reddit_clone.vote;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;

import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/votes")
public class VoteController {
	private final VoteService voteService;

	@PostMapping
	public ResponseEntity<String> vote(VoteRequest voteRequest) {
		voteService.vote(voteRequest);
		return new ResponseEntity<>("Voted!", HttpStatus.CREATED);
	}
}