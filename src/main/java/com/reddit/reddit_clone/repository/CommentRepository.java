package com.reddit.reddit_clone.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.reddit.reddit_clone.model.Comment;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
}