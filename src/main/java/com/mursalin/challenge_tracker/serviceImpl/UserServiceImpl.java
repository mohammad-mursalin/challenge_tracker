package com.mursalin.challenge_tracker.serviceImpl;

import com.mursalin.challenge_tracker.DTO.LoginRegisterRequest;
import com.mursalin.challenge_tracker.model.User;
import com.mursalin.challenge_tracker.repository.UserRepository;
import com.mursalin.challenge_tracker.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);

    private UserRepository repository;

    private AuthenticationManager authenticationManager;

    private JwtService jwtService;

    @Autowired
    public void setJwtService(JwtService jwtService) {
        this.jwtService = jwtService;
    }

    @Autowired
    public void setAuthenticationManager(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    public UserServiceImpl(UserRepository repository) {
        this.repository = repository;
    }

    @Override
    public ResponseEntity<String> register(LoginRegisterRequest user) {
        User newUser = new User();
        newUser.setPassword(encoder.encode(user.getPassword()));
        newUser.setRole("USER");
        repository.save(newUser);
        return new ResponseEntity<>("user registration successfull", HttpStatus.CREATED);
    }

    @Override
    public List<User> getUsers() {
        return repository.findAll();
    }

    @Override
    public ResponseEntity<String> login(LoginRegisterRequest user) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword()));
        if(authentication.isAuthenticated()) {
            String jwtToken = jwtService.generateToken(user.getEmail());
            return new ResponseEntity<>(jwtToken, HttpStatus.OK);
        }
        return new ResponseEntity<>("Login failed", HttpStatus.BAD_REQUEST);
    }
}
