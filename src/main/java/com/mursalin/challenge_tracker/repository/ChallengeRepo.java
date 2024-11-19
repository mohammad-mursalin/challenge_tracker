package com.mursalin.challenge_tracker.repository;

import com.mursalin.challenge_tracker.DTO.ChallengeDTO;
import com.mursalin.challenge_tracker.model.Challenges;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ChallengeRepo extends JpaRepository<Challenges, Long> {

    List<Challenges> findByMonthIgnoreCase(String month);

    @Query("SELECT new com.mursalin.challenge_tracker.DTO.ChallengeDTO(c.challengeId, c.month, c.challenge) FROM Challenges c WHERE c.user.userId = :userId")
    List<ChallengeDTO> findByUser_UserId(long userId);

    @Query("SELECT new com.mursalin.challenge_tracker.DTO.ChallengeDTO(c.challengeId, c.month, c.challenge) FROM Challenges c WHERE c.user.userId = :userId AND LOWER(c.month) = LOWER(:month)")
    List<ChallengeDTO> findByUser_UserIdAndMonthIgnoreCase(long userId, String month);

    boolean existsByChallengeIdAndUserUserId(long challengeId, long userId);


}

