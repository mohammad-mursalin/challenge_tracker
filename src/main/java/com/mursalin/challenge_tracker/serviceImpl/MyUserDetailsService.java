package com.mursalin.challenge_tracker.serviceImpl;

import com.mursalin.challenge_tracker.model.User;
import com.mursalin.challenge_tracker.model.UserPrinciples;
import com.mursalin.challenge_tracker.repository.UserRepository;
import com.mursalin.challenge_tracker.service.UserService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;

public class MyUserDetailsService implements UserDetailsService {
    
    private UserRepository repo;

    public MyUserDetailsService(UserRepository repo) {
        this.repo = repo;
    }

    @Override
    public UserDetails loadUserByUsername(String user_mail) throws UsernameNotFoundException {

        Optional<User> user = repo.findByUser_mail(user_mail);

        if(user.isPresent())
            return new UserPrinciples(user.get());
        else {
            throw new UsernameNotFoundException("User not found");
        }
    }
}
