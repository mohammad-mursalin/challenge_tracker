package com.mursalin.challenge_tracker.serviceImpl;

import com.mursalin.challenge_tracker.model.Challenges;
import com.mursalin.challenge_tracker.repository.ChallengeRepo;
import com.mursalin.challenge_tracker.service.ChallengeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ChallengeServiceImpl implements ChallengeService {

    private ChallengeRepo repo;

    public ChallengeServiceImpl(ChallengeRepo repo) {
        this.repo = repo;
    }

    @Override
    public ResponseEntity<List<Challenges>> getChallenges() {
        return new ResponseEntity<>(repo.findAll(), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<List<Challenges>> getChallengesByMonth(String mail, String month) {
        List<Challenges> challenges = repo.findByMonthIgnoreCase(month);

        if(challenges.isEmpty())
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        return new ResponseEntity<>(challenges, HttpStatus.FOUND);
    }

    @Override
    public ResponseEntity<String> addChallenge(String mail, Challenges challenge) {
        Challenges savedChallenge = repo.save(challenge);

        return new ResponseEntity<>("challenge saved successfully", HttpStatus.CREATED);

    }

    @Override
    public ResponseEntity<String> updateChallenge(Challenges updatedChallenge, long id) {
        Challenges challenge = repo.findById(id).orElse(null);

        if(challenge != null) {
            updatedChallenge.setId(id);
            repo.save(updatedChallenge);

            return new ResponseEntity<>("Challenge updated", HttpStatus.OK);
        }
        return new ResponseEntity<>("No content found with given id", HttpStatus.NOT_FOUND);
    }

    @Override
    public ResponseEntity<String> deleteChallenge(long id) {
        if(repo.existsById(id)){
            repo.deleteById(id);
            return new ResponseEntity<>("Challenge deleted", HttpStatus.OK);
        }
        return new ResponseEntity<>("Content not found", HttpStatus.NOT_FOUND);
    }

    @Override
    public ResponseEntity<List<Challenges>> getUserChallenges(String mail) {
        return null;
    }
}
