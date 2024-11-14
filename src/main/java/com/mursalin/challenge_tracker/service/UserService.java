package com.mursalin.challenge_tracker.service;

import com.mursalin.challenge_tracker.model.User;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

public interface UserService {
    ResponseEntity<String> register(User user);

    List<User> getUsers();
}
