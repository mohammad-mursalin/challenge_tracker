package com.mursalin.challenge_tracker.serviceImpl;

import com.mursalin.challenge_tracker.model.User;
import com.mursalin.challenge_tracker.repository.UserRepository;
import com.mursalin.challenge_tracker.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);

    private UserRepository repository;

    public UserServiceImpl(UserRepository repository) {
        this.repository = repository;
    }

    @Override
    public ResponseEntity<String> register(User user) {
        user.setPassword(encoder.encode(user.getPassword()));
        user.setRole("USER");
        repository.save(user);
        return new ResponseEntity<>("user registration successfull", HttpStatus.CREATED);
    }

    @Override
    public List<User> getUsers() {
        return repository.findAll();
    }
}
