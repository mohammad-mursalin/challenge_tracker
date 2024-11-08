package com.mursalin.challenge_tracker.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class Challenges {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String month;
    private String challenge;

}
