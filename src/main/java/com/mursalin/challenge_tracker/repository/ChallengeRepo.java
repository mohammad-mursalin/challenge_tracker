package com.mursalin.challenge_tracker.repository;

import com.mursalin.challenge_tracker.model.Challenges;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChallengeRepo extends JpaRepository<Challenges, Long> {
}
