package com.mursalin.challenge_tracker.controller;

import com.mursalin.challenge_tracker.model.Challenges;
import com.mursalin.challenge_tracker.service.ChallengeService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/challenge")
public class ChallengeController {

    private ChallengeService service;

    public ChallengeController(ChallengeService service) {
        this.service = service;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/admin")
    public ResponseEntity<List<Challenges>> getChallenges() {
        return service.getChallenges();
    }

    @GetMapping("/user")
    public ResponseEntity<List<Challenges>> getUserChallenges(@AuthenticationPrincipal UserDetails userDetails) {
        String mail = userDetails.getUsername();
        return service.getUserChallenges(mail);
    }

    @GetMapping("/user/{month}")
    public ResponseEntity<List<Challenges>> getChallengesByMonth(@PathVariable String month,
                                                                 @AuthenticationPrincipal UserDetails userDetails) {
        String mail = userDetails.getUsername();
        return service.getChallengesByUserAndMonth(mail, month);
    }

    @PostMapping("/user")
    public ResponseEntity<String> addChallenge(@RequestBody Challenges challenge,
                                               @AuthenticationPrincipal UserDetails userDetails) {
        String mail = userDetails.getUsername();
        return service.addChallengeForUser(mail, challenge);
    }

    @PutMapping("/user/{id}")
    public ResponseEntity<String> updateChallenge(@RequestBody Challenges updatedChallenge, @PathVariable long id) {
        return service.updateChallenge(updatedChallenge, id);
    }

    @DeleteMapping("/user/{id}")
    public ResponseEntity<String> deleteChallenge(@PathVariable long id) {
        return service.deleteChallenge(id);
    }
}
