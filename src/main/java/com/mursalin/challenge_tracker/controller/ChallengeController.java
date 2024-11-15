package com.mursalin.challenge_tracker.controller;

import com.mursalin.challenge_tracker.model.Challenges;
import com.mursalin.challenge_tracker.service.ChallengeService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/challenge")
public class ChallengeController {

    private ChallengeService service;

    public ChallengeController(ChallengeService service) {
        this.service = service;
    }

    @GetMapping("/")
    public ResponseEntity<List<Challenges>> getChallenges() {
        return service.getChallenges();
    }

    @GetMapping("/{month}")
    public ResponseEntity<List<Challenges>> getChallengesByMonth(@PathVariable String month) {
        return service.getChallengesByMonth(month);
    }

    @PostMapping("/")
    public ResponseEntity<String> addChallenge(@RequestBody Challenges challenge) {
        return service.addChallenge(challenge);
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateChallenge(@RequestBody Challenges updatedChallenge, @PathVariable long id) {
        return service.updateChallenge(updatedChallenge, id);
    }

    @DeleteMapping("/admin/{id}")
    public ResponseEntity<String> deleteChallenge(@PathVariable long id) {
        return service.deleteChallenge(id);
    }
}
