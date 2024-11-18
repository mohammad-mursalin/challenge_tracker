package com.mursalin.challenge_tracker.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Challenges {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long challengeId;

    private String month;
    private String challenge;

    @ManyToOne
    @JoinColumn(nullable = false)
    private User user;
}
