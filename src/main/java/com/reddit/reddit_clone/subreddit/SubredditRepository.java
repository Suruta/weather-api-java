package com.reddit.reddit_clone.subreddit;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

// import com.reddit.reddit_clone.subreddit.Subreddit;

@Repository
public interface SubredditRepository extends JpaRepository<Subreddit, Long> {
}