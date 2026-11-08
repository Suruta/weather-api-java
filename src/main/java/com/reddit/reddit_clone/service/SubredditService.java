package com.reddit.reddit_clone.service;

import java.time.Instant;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.reddit.reddit_clone.dto.SubredditRequest;
import com.reddit.reddit_clone.dto.SubredditResponse;
import com.reddit.reddit_clone.model.Subreddit;
import com.reddit.reddit_clone.model.User;
import com.reddit.reddit_clone.repository.PostRepository;
import com.reddit.reddit_clone.repository.SubredditRepository;
import com.reddit.reddit_clone.repository.UserRepository;
import com.reddit.reddit_clone.exceptions.SpringRedditException;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class SubredditService {
	private final SubredditRepository subredditRepository;
	private final UserRepository userRepository;
	private final PostRepository postRepository;

	@Transactional
	public void create(SubredditRequest subredditRequest) {
		Subreddit subreddit = new Subreddit();
		apply(subreddit, subredditRequest);
		subreddit.setCreatedDate(Instant.now());

		subredditRepository.save(subreddit);
	}

	@Transactional(readOnly = true)
	public List<SubredditResponse> getAll() {
		return subredditRepository.findAll().stream().map(this::toResponse).toList();
	}

	@Transactional(readOnly = true)
	public SubredditResponse getById(Long id) {
		Subreddit subreddit = find(id);
		return toResponse(subreddit);
	}

	@Transactional
	public void update(Long id, SubredditRequest subredditRequest) {
		Subreddit subreddit = find(id);
		apply(subreddit, subredditRequest);
		subredditRepository.save(subreddit);
	}

	@Transactional
	public void delete(Long id) {
		subredditRepository.delete(find(id));
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

	private Subreddit find(Long id) {
		return subredditRepository.findById(id)
			.orElseThrow(() -> new SpringRedditException("Subreddit not found"));
	}

	private SubredditResponse toResponse(Subreddit subreddit) {
		return SubredditResponse.builder()
			.subredditId(subreddit.getSubredditId())
			.name(subreddit.getName())
			.description(subreddit.getDescription())
			.userId(subreddit.getUser() != null ? subreddit.getUser().getUserId() : null)
			.createdDate(subreddit.getCreatedDate())
			.build();
	}
}