package com.mursalin.challenge_tracker.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChallengeDTO {

    private long challengeId;
    private String month;
    private String challenge;
}
