package com.mursalin.challenge_tracker.repository;

import com.mursalin.challenge_tracker.model.Challenges;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChallengeRepo extends JpaRepository<Challenges, Long> {
    List<Challenges> findByMonthIgnoreCase(String month);

    List<Challenges> findByUser_UserId(long userId);

    List<Challenges> findByUser_UserIdAndMonthIgnoreCase(long userId, String month);
}

