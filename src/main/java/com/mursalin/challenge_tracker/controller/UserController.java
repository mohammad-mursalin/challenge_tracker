package com.mursalin.challenge_tracker.controller;

import com.mursalin.challenge_tracker.DTO.LoginRegisterRequest;
import com.mursalin.challenge_tracker.model.User;
import com.mursalin.challenge_tracker.repository.UserRepository;
import com.mursalin.challenge_tracker.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UserController {

    private UserService service;

    public UserController(UserService service) {
        this.service = service;
    }

    @PostMapping("/user/register")
    public ResponseEntity<String> register(@Valid @RequestBody LoginRegisterRequest user) {
        return service.register(user);
    }

    @PostMapping("/user/login")
    public ResponseEntity<String> login(@Valid @RequestBody LoginRegisterRequest user) {
        return service.login(user);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/admin/users")
    public List<User> userList() {
        return service.getUsers();
    }
}
