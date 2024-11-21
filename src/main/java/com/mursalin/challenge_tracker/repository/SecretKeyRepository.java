package com.mursalin.challenge_tracker.repository;

import com.mursalin.challenge_tracker.model.UserSecretKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SecretKeyRepository extends JpaRepository<UserSecretKey, Long> {
    String findSecretKeyByUser_UserId(long userId);
}
