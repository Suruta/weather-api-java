package com.reddit.reddit_clone.service;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.reddit.reddit_clone.dto.PostRequest;
import com.reddit.reddit_clone.dto.PostResponse;
import com.reddit.reddit_clone.model.Post;
import com.reddit.reddit_clone.model.Subreddit;
import com.reddit.reddit_clone.model.User;
import com.reddit.reddit_clone.repository.PostRepository;
import com.reddit.reddit_clone.repository.SubredditRepository;
import com.reddit.reddit_clone.repository.UserRepository;
import com.reddit.reddit_clone.exceptions.SpringRedditException;

import org.springframework.transaction.annotation.Transactional;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class PostService {
	private final PostRepository postRepository;
	private final SubredditRepository subredditRepository;
	private final UserRepository userRepository;

	@Transactional
	public void create(PostRequest postRequest) {
		Post post = new Post();
		apply(post, postRequest);
		post.setCreatedDate(Instant.now());

		postRepository.save(post);
	}

	@Transactional(readOnly = true)
	public List<PostResponse> getAll() {
		return postRepository.findAll().stream().map(this::toResponse).toList();
	}

	@Transactional(readOnly = true)
	public PostResponse getById(Long id) {
		return toResponse(find(id));
	}

	@Transactional
	public void update(Long id, PostRequest postRequest) {
		Post post = find(id);
		apply(post, postRequest);
		postRepository.save(post);
	}

	@Transactional
	public void delete(Long id) {
		postRepository.delete(find(id));
	}

	private void apply(Post post, PostRequest postRequest) {
		Subreddit subreddit = subredditRepository.findById(postRequest.getSubredditId())
			.orElseThrow(() -> new SpringRedditException("Subreddit not found"));

		post.setPostName(postRequest.getPostName());
		post.setUrl(postRequest.getUrl());
		post.setDescription(postRequest.getDescription());
		post.setSubreddit(subreddit);

		if (postRequest.getUserId() != null) {
			User user = userRepository.findById(postRequest.getUserId())
				.orElseThrow(() -> new SpringRedditException("User not found"));
			post.setUser(user);
		}
	}

	private Post find(Long id) {
		return postRepository.findById(id)
			.orElseThrow(() -> new SpringRedditException("Post not found"));
	}

	private PostResponse toResponse(Post post) {
		return PostResponse.builder()
			.postId(post.getPostId())
			.postName(post.getPostName())
			.url(post.getUrl())
			.description(post.getDescription())
			.voteCount(post.getVoteCount())
			.userId(post.getUser() != null ? post.getUser().getUserId() : null)
			.username(post.getUser() != null ? post.getUser().getUsername() : null)
			.subredditId(post.getSubreddit() != null ? post.getSubreddit().getSubredditId() : null)
			.subredditName(post.getSubreddit() != null ? post.getSubreddit().getName() : null)
			.createdDate(post.getCreatedDate())
			.build();
	}
}









