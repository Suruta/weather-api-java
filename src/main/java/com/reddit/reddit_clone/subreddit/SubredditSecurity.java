package com.reddit.reddit_clone.subreddit;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component("subredditSecurity")
@AllArgsConstructor
@Slf4j
public class SubredditSecurity {
	private final SubredditRepository subredditRepository;

	@Transactional(readOnly = true)
	public boolean isOwner(Long subredditId, String username) {
		log.info("isOwner id={} username={}", subredditId, username);
		return subredditRepository.findById(subredditId)
			.map(p -> p.getUser().getUsername().equals(username))
			.orElse(false);
	}
}