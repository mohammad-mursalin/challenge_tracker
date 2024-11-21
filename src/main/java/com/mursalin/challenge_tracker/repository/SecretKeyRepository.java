package com.mursalin.challenge_tracker.repository;

import com.mursalin.challenge_tracker.model.UserSecretKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface SecretKeyRepository extends JpaRepository<UserSecretKey, Long> {
    @Query("SELECT u.secretKey FROM UserSecretKey u WHERE u.jwt = :jwt")
    String findSecretKeyByJwt(String jwt);

    @Modifying
    @Query("UPDATE UserSecretKey u SET u.secretKey = :newSecretKey, u.jwt = :newJwt WHERE u.user.userId = :userId")
    int updateSecretKeyAndJwt(@Param("userId") Long userId, @Param("newSecretKey") String newSecretKey, @Param("newJwt") String newJwt);

    boolean existsByUser_UserId(Long userId);
}
