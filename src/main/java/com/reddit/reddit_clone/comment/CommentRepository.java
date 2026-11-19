package com.reddit.reddit_clone.comment;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

// import com.reddit.reddit_clone.comment.Comment;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
}