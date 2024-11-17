package com.mursalin.challenge_tracker.service;

import com.mursalin.challenge_tracker.model.Challenges;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface ChallengeService {
    ResponseEntity<List<Challenges>> getChallenges();

    ResponseEntity<List<Challenges>> getChallengesByMonth(String mail, String month);

    ResponseEntity<String> addChallenge(String mail, Challenges challenge);

    ResponseEntity<String> updateChallenge(Challenges updateChallenge, long id);

    ResponseEntity<String> deleteChallenge(long id);

    ResponseEntity<List<Challenges>> getUserChallenges(String mail);
}
