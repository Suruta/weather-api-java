package com.reddit.reddit_clone.subreddit;

import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;

@Component("subredditSecurity")
@AllArgsConstructor
public class SubredditSecurity {
	private final SubredditRepository subredditRepository;

	public boolean isOwner(Long subredditId, String username) {
		return subredditRepository.findById(subredditId)
			.map(p -> p.getUser().getUsername().equals(username))
			.orElse(false);
	}
}