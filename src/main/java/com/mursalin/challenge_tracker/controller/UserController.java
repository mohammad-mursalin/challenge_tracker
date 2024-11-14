package com.mursalin.challenge_tracker.controller;

import com.mursalin.challenge_tracker.model.User;
import com.mursalin.challenge_tracker.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UserController {

    private UserService service;

    public UserController(UserService service) {
        this.service = service;
    }

    @PostMapping("/user/register")
    public ResponseEntity<String> register(@RequestBody User user) {
        return service.register(user);
    }

    @GetMapping("/users")
    public List<User> userList() {
        return service.getUsers();
    }
}
