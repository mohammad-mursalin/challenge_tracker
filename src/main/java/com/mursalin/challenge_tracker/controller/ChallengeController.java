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

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/admin/{month}")
    public ResponseEntity<List<Challenges>> getChallengesByMonth(@PathVariable String month) {
        return service.getChallengesByMonth(month);
    }

    @GetMapping("/user/{month}")
    public ResponseEntity<Object> getChallengesByMonth(@PathVariable String month,
                                                                 @AuthenticationPrincipal UserDetails userDetails) {
        String mail = userDetails.getUsername();
        return service.getChallengesByUserAndMonth(mail, month);
    }

    @PreAuthorize("hasRole('USER')")
    @PostMapping("/user")
    public ResponseEntity<String> addChallenge(@RequestBody Challenges challenge,
                                               @AuthenticationPrincipal UserDetails userDetails) {
        String mail = userDetails.getUsername();
        return service.addChallenge(mail, challenge);
    }

    @PutMapping("/user/{id}")
    public ResponseEntity<String> updateChallenge(@AuthenticationPrincipal UserDetails userDetails, @RequestBody Challenges updatedChallenge, @PathVariable long id) {
        String mail = userDetails.getUsername();
        return service.updateChallenge(mail, updatedChallenge, id);
    }

    @DeleteMapping("/user/{id}")
    public ResponseEntity<String> deleteChallenge(@AuthenticationPrincipal UserDetails userDetails, @PathVariable long id) {
        String mail = userDetails.getUsername();
        return service.deleteChallenge(mail, id);
    }
}
