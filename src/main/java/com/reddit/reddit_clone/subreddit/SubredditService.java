package com.reddit.reddit_clone.subreddit;

import java.time.Instant;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.reddit.reddit_clone.user.User;
import com.reddit.reddit_clone.post.PostRepository;
import com.reddit.reddit_clone.user.UserRepository;
import com.reddit.reddit_clone.auth.AuthService;
import com.reddit.reddit_clone.exception.SpringRedditException;

// import com.reddit.reddit_clone.subreddit.SubredditRequest;
// import com.reddit.reddit_clone.subreddit.SubredditResponse;
// import com.reddit.reddit_clone.subreddit.Subreddit;
// import com.reddit.reddit_clone.subreddit.SubredditRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class SubredditService {
	private final SubredditRepository subredditRepository;
	private final UserRepository userRepository;
	private final PostRepository postRepository;
	private final AuthService authService;

	@Transactional
	public void createSubreddit(SubredditRequest subredditRequest) {
		Subreddit subreddit = new Subreddit();
		apply(subreddit, subredditRequest);
		subreddit.setCreatedDate(Instant.now());

		subredditRepository.save(subreddit);
	}

	@Transactional(readOnly = true)
	public List<SubredditResponse> getSubreddits() {
		return subredditRepository.findAll().stream().map(this::toResponse).toList();
	}

	@Transactional(readOnly = true)
	public SubredditResponse getSubredditById(Long id) {
		Subreddit subreddit = find(id);
		return toResponse(subreddit);
	}

	@Transactional
	public void updateSubreddit(Long id, SubredditRequest subredditRequest) {
		Subreddit subreddit = find(id);
		apply(subreddit, subredditRequest);
		subredditRepository.save(subreddit);
	}

	@Transactional
	public void deleteSubreddit(Long id) {
		subredditRepository.delete(find(id));
	}

	private void apply(Subreddit subreddit, SubredditRequest subredditRequest) {
		subreddit.setName(subredditRequest.getName());
		subreddit.setDescription(subredditRequest.getDescription());

		User user = authService.getCurrentUser();
		subreddit.setUser(user);
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