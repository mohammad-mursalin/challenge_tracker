package com.mursalin.challenge_tracker.repository;

import com.mursalin.challenge_tracker.DTO.UserDTO;
import com.mursalin.challenge_tracker.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    @Query("SELECT new com.mursalin.challenge_tracker.DTO.UserDTO(u.userId, u.email, u.password, u.role) FROM User u WHERE u.email = :email")
    Optional<UserDTO> findUserDTOByEmail(String email);

    User findByEmail(String email);

    @Query("SELECT u.userId FROM User u WHERE u.email = :email")
    Long findUserIdByEmail(String email);

}
