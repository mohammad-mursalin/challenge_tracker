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
    private long id;

    private String month;
    private String challenge;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
}
