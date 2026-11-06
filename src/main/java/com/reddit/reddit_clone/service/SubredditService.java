package com.reddit.reddit_clone.service;

import java.time.Instant;
import java.util.List;

import org.springframework.stereotype.Service;

import com.reddit.reddit_clone.dto.SubredditRequest;
import com.reddit.reddit_clone.model.Subreddit;
import com.reddit.reddit_clone.model.User;
import com.reddit.reddit_clone.repository.SubredditRepository;
import com.reddit.reddit_clone.repository.UserRepository;
import com.reddit.reddit_clone.exceptions.SpringRedditException;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class SubredditService {
	private final SubredditRepository subredditRepository;
	private final UserRepository userRepository;

	public void create(SubredditRequest subredditRequest) {
		Subreddit subreddit = new Subreddit();
		apply(subreddit, subredditRequest);
		subreddit.setCreatedDate(Instant.now());

		subredditRepository.save(subreddit);
	}

	public List<Subreddit> getAll() {
		return subredditRepository.findAll();
	}

	public Subreddit getById(Long id) {
		return subredditRepository.findById(id)
			.orElseThrow(() -> new SpringRedditException("Subreddit not found"));
	}

	public void update(Long id, SubredditRequest subredditRequest) {
		Subreddit subreddit = subredditRepository.findById(id)
			.orElseThrow(() -> new SpringRedditException("Subreddit not found"));

		apply(subreddit, subredditRequest);
	}

	public void delete(Long id) {
		subredditRepository.deleteById(id);
	}

	private void apply(Subreddit subreddit, SubredditRequest subredditRequest) {
		subreddit.setName(subredditRequest.getName());
		subreddit.setDescription(subredditRequest.getDescription());

		if (subredditRequest.getUserId() != null) {
			User user = userRepository.findById(subredditRequest.getUserId())
				.orElseThrow(() -> new SpringRedditException("User not found"));
			subreddit.setUser(user);
		}
	}
}