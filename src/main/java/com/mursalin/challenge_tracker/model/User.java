package com.mursalin.challenge_tracker.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;

@Entity
@Table(name = "challenge_users")
@Data
@AllArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NonNull
    private String userMail;
    @NonNull
    private String password;

    private String role;
}
