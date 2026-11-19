package com.reddit.reddit_clone.vote;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

// import com.reddit.reddit_clone.vote.Vote;

@Repository
public interface VoteRepository extends JpaRepository<Vote, Long> {
}