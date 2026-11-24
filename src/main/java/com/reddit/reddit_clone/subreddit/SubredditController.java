package com.reddit.reddit_clone.subreddit;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

// import com.reddit.reddit_clone.subreddit.SubredditRequest;
// import com.reddit.reddit_clone.subreddit.SubredditResponse;
// import com.reddit.reddit_clone.subreddit.SubredditService;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/api/v1/subreddits")
@AllArgsConstructor
public class SubredditController {
	private final SubredditService subredditService;

	@PostMapping
	public ResponseEntity<String> createSubreddit(@RequestBody SubredditRequest subredditRequest) {
		subredditService.createSubreddit(subredditRequest);
		return new ResponseEntity<>("Subreddit created successfully", HttpStatus.CREATED);
	}

	@GetMapping
	public List<SubredditResponse> getSubreddits() {
		return subredditService.getSubreddits();
	}

	@GetMapping("/{id}")
	public SubredditResponse getSubredditById(@PathVariable Long id) {
		return subredditService.getSubredditById(id);
	}

	@PutMapping("/{id}")
	@PreAuthorize("hasRole('ADMIN') or @subredditSecurity.isOwner(#id, authentication.name")
	public ResponseEntity<String> updateSubreddit(@PathVariable Long id, @RequestBody SubredditRequest subredditRequest) {
		subredditService.updateSubreddit(id, subredditRequest);
		return new ResponseEntity<>("Subreddit updated successfully", HttpStatus.OK);
	}

	@DeleteMapping("/{id}")
	@PreAuthorize("hasRole('ADMIN') or @subredditSecurity.isOwner(#id, authentication.name")
	public ResponseEntity<String> deleteSubreddit(@PathVariable Long id) {
		subredditService.deleteSubreddit(id);
		return new ResponseEntity<>("Subreddit deleted successfully", HttpStatus.OK);
	}
}








