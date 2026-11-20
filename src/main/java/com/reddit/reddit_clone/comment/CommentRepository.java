package com.reddit.reddit_clone.comment;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.reddit.reddit_clone.post.Post;
import com.reddit.reddit_clone.user.User;

// import com.reddit.reddit_clone.comment.Comment;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
	List<Comment> findByPost(Post post);
	List<Comment> findAllByUser(User user);
}