package com.mursalin.challenge_tracker.repository;

import com.mursalin.challenge_tracker.model.Challenges;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChallengeRepo extends JpaRepository<Challenges, Long> {
    List<Challenges> findByMonthIgnoreCase(String month);

    List<Challenges> findByUserId(long userId);

    List<Challenges> findByUserIdAndMonthIgnoreCase(long userId, String month);

}
