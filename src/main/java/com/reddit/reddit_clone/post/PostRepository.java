package com.reddit.reddit_clone.post;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

// import com.reddit.reddit_clone.post.Post;
import com.reddit.reddit_clone.subreddit.Subreddit;
import com.reddit.reddit_clone.user.User;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
	// boolean existsBySubreddit_SubredditId(Long subredditId);
	List<Post> findAllBySubreddit(Subreddit subreddit);
	List<Post> findByUser(User user);
}