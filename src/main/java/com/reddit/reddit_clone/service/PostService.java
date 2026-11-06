package com.reddit.reddit_clone.service;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.reddit.reddit_clone.dto.PostRequest;
import com.reddit.reddit_clone.model.Post;
import com.reddit.reddit_clone.model.Subreddit;
import com.reddit.reddit_clone.model.User;
import com.reddit.reddit_clone.repository.PostRepository;
import com.reddit.reddit_clone.repository.SubredditRepository;
import com.reddit.reddit_clone.repository.UserRepository;
import com.reddit.reddit_clone.exceptions.SpringRedditException;

import jakarta.transaction.Transactional;
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

	public List<Post> getAll() {
		return postRepository.findAll();
	}

	public Post getById(Long id) {
		return postRepository.findById(id)
			.orElseThrow(() -> new SpringRedditException("Post not found"));
	}

	public void update(Long id, PostRequest postRequest) {
		Post post = postRepository.findById(id)
			.orElseThrow(() -> new SpringRedditException("Post not found"));

		apply(post, postRequest);
	}

	public void delete(Long id) {
		postRepository.deleteById(id);
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
}









