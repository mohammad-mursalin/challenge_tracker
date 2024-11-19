package com.mursalin.challenge_tracker.serviceImpl;

import com.mursalin.challenge_tracker.model.Challenges;
import com.mursalin.challenge_tracker.model.User;
import com.mursalin.challenge_tracker.repository.ChallengeRepo;
import com.mursalin.challenge_tracker.repository.UserRepository;
import com.mursalin.challenge_tracker.service.ChallengeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ChallengeServiceImpl implements ChallengeService {

    private ChallengeRepo repo;

    private UserRepository userRepo;

    @Autowired
    public void setRepo(ChallengeRepo repo) {
        this.repo = repo;
    }

    @Autowired
    public void setUserRepo(UserRepository userRepo) {
        this.userRepo = userRepo;
    }

    @Override
    public ResponseEntity<List<Challenges>> getChallenges() {
        return new ResponseEntity<>(repo.findAll(), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<List<Challenges>> getChallengesByMonth(String month) {
        List<Challenges> challenges = repo.findByMonthIgnoreCase(month);

        if(challenges.isEmpty())
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        return new ResponseEntity<>(challenges, HttpStatus.FOUND);
    }

    @Override
    public ResponseEntity<String> addChallenge(String mail, Challenges challenge) {
        Optional<User> user = userRepo.findByEmail(mail);

        if(user.isPresent()) {
            challenge.setUser(user.get());
            user.get().getChallenges().add(challenge);
            repo.save(challenge);
            return new ResponseEntity<>("challenge saved successfully", HttpStatus.CREATED);
        }
        return ResponseEntity.notFound().build();
    }

    @Override
    public ResponseEntity<String> updateChallenge(String mail, Challenges updatedChallenge, long id) {
        long userId = getUserId(mail);

        if (repo.existsByChallengeIdAndUserUserId(id, userId)) {

            Challenges challenge = repo.findById(id).orElse(null);

            if (challenge != null) {
                challenge.setMonth(updatedChallenge.getMonth());
                challenge.setChallenge(updatedChallenge.getChallenge());
                repo.save(challenge);
                return new ResponseEntity<>("Challenge updated", HttpStatus.OK);
            }
            return new ResponseEntity<>("Challenge not found", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>("Challenge not found or unauthorized", HttpStatus.NOT_FOUND);
    }


    @Override
    public ResponseEntity<String> deleteChallenge(String mail, long id) {
        long userId = getUserId(mail);

        if(repo.existsByChallengeIdAndUserUserId(id, userId)){
            repo.deleteById(id);
            return new ResponseEntity<>("Challenge deleted", HttpStatus.OK);
        }
        return new ResponseEntity<>("Content not found", HttpStatus.NOT_FOUND);


    }

    @Override
    public ResponseEntity<List<Challenges>> getUserChallenges(String mail) {
        long userId = getUserId(mail);

        List<Challenges> challenges = repo.findByUser_UserId(userId);
        return new ResponseEntity<>(challenges, HttpStatus.OK);

    }

    @Override
    public ResponseEntity<Object> getChallengesByUserAndMonth(String mail, String month) {
        long userId = getUserId(mail);

        List<Challenges> challenges = repo.findByUser_UserIdAndMonthIgnoreCase(userId, month);

        if(challenges.isEmpty())
            return new ResponseEntity<>("no challenge found for the given month", HttpStatus.NO_CONTENT);
        return new ResponseEntity<>(challenges, HttpStatus.OK);
    }

    private long getUserId(String mail) {
        return userRepo.findUserIdByEmail(mail);
    }
}
