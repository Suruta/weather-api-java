package com.reddit.reddit_clone.vote;

import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.reddit.reddit_clone.auth.AuthService;
import com.reddit.reddit_clone.exception.SpringRedditException;
import com.reddit.reddit_clone.post.Post;
import com.reddit.reddit_clone.post.PostRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class VoteService {
	private final VoteRepository voteRepository;
	private final AuthService authService;
	private final PostRepository postRepository;

	@Transactional
	public void vote(VoteRequest voteRequest) {
		Post post = postRepository.findById(voteRequest.getPostId())
			.orElseThrow(() -> new SpringRedditException("Post not found with id: " + voteRequest.getPostId()));

		Optional<Vote> voteByPostAndUser = voteRepository.findTopByPostAndUserOrderByVoteIdDesc(post, authService.getCurrentUser());

		if (voteByPostAndUser.isPresent() && 
			voteByPostAndUser.get().getVoteType()
			.equals(voteRequest.getVoteType())) {
			throw new SpringRedditException("You have already " + voteRequest.getVoteType() + "'d for this post");
		}

		if (VoteType.UPVOTE.equals(voteRequest.getVoteType())) {
			post.setVoteCount(post.getVoteCount() + 1);
		} else {
			post.setVoteCount(post.getVoteCount() - 1);
		}

		Vote vote = new Vote();
		apply(vote, voteRequest, post);

		voteRepository.save(vote);
		postRepository.save(post);
	}

	private void apply(Vote vote, VoteRequest voteRequest, Post post) {
		vote.setVoteType(voteRequest.getVoteType());
		vote.setPost(post);
		vote.setUser(authService.getCurrentUser());
	}
}














