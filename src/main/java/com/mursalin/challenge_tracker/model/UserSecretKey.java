package com.mursalin.challenge_tracker.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserSecretKey {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long keyId;
    private String secretKey;
    private String jwt;
    @OneToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
}
