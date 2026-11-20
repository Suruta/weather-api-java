package com.reddit.reddit_clone.comment;

import java.time.Instant;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.reddit.reddit_clone.exception.SpringRedditException;
import com.reddit.reddit_clone.post.Post;
import com.reddit.reddit_clone.post.PostRepository;
import com.reddit.reddit_clone.user.User;
import com.reddit.reddit_clone.user.UserRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class CommentService {
	private final CommentRepository commentRepository;
	private final PostRepository postRepository;
	private final UserRepository userRepository;

	@Transactional
	public void createComment(CommentRequest commentRequest) {
		Comment comment = new Comment();
		apply(comment, commentRequest);
		comment.setCreatedDate(Instant.now());

		commentRepository.save(comment);
	}

	@Transactional(readOnly = true)
	public List<CommentResponse> getCommentsForPost(Long postId) {
		Post post = postRepository.findById(postId).orElseThrow(() -> new SpringRedditException("Post not found"));

		List<Comment> comments = commentRepository.findByPost(post);
		return comments.stream().map(this::toResponse).toList();
	}

	@Transactional(readOnly = true)
	public List<CommentResponse> getCommentsByUsername(String username) {
		User user = userRepository.findByUsername(username)
			.orElseThrow(() -> new SpringRedditException("User not found"));

		List<Comment> comments = commentRepository.findAllByUser(user);
		return comments.stream().map(this::toResponse).toList();
	}

	private void apply(Comment comment, CommentRequest commentRequest) {
		Post post = postRepository.findById(commentRequest.getPostId())
			.orElseThrow(() -> new SpringRedditException("Post not found"));

		comment.setText(commentRequest.getText());
		comment.setPost(post);;

		if (commentRequest.getUserId() != null) {
			User user = userRepository.findById(commentRequest.getUserId())
				.orElseThrow(() -> new SpringRedditException("User not found"));
			comment.setUser(user);
		}
	}

	private CommentResponse toResponse(Comment comment) {
		return CommentResponse.builder()
			.commentId(comment.getCommentId())
			.text(comment.getText())
			.postId(comment.getPost() != null ? comment.getPost().getPostId() : null)
			.postName(comment.getPost() != null ? comment.getPost().getPostName() : null)
			.userId(comment.getUser() != null ? comment.getUser().getUserId() : null)
			.username(comment.getUser() != null ? comment.getUser().getUsername() : null)
			.createdDate(comment.getCreatedDate())
			.build();
	}
}














