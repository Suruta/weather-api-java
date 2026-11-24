package com.reddit.reddit_clone.post;

import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;

@Component("postSecurity")
@AllArgsConstructor
public class PostSecurity {
	private final PostRepository postRepository;

	public boolean isOwner(Long postId, String username) {
		return postRepository.findById(postId)
			.map(p -> p.getUser().getUsername().equals(username))
			.orElse(false);
	}
}