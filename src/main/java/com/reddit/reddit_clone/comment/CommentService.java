package com.reddit.reddit_clone.comment;

import java.time.Instant;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.reddit.reddit_clone.auth.AuthService;
import com.reddit.reddit_clone.exception.SpringRedditException;
import com.reddit.reddit_clone.mail.MailContentBuilder;
import com.reddit.reddit_clone.mail.MailService;
import com.reddit.reddit_clone.mail.NotificationEmail;
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
	private final AuthService authService;
	private final MailService mailService;
	private final MailContentBuilder mailContentBuilder;

	@Transactional
	public void createComment(CommentRequest commentRequest) {
		Comment comment = new Comment();
		Post post = apply(comment, commentRequest);
		comment.setCreatedDate(Instant.now());

		commentRepository.save(comment);

		String message = mailContentBuilder.build(post.getUser().getUsername() + " posted a comment on your post.");
		sendCommentNotification(message, post.getUser());
	}

	private void sendCommentNotification(String message, User user) {
		mailService.sendMail(new NotificationEmail(user.getUsername() + "Commented on your post", user.getEmail(), message));
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

	private Post apply(Comment comment, CommentRequest commentRequest) {
		Post post = postRepository.findById(commentRequest.getPostId())
			.orElseThrow(() -> new SpringRedditException("Post not found"));

		comment.setText(commentRequest.getText());
		comment.setPost(post);;

		User user = authService.getCurrentUser();
		comment.setUser(user);

		return post;
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














