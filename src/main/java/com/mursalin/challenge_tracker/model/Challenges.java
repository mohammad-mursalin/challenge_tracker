package com.mursalin.challenge_tracker.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class Challenges {

    @Id
    private long id;
    private String month;
    private String challenge;

}
