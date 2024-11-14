package com.mursalin.challenge_tracker.serviceImpl;

import com.mursalin.challenge_tracker.model.User;
import com.mursalin.challenge_tracker.model.UserPrinciples;
import com.mursalin.challenge_tracker.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MyUserDetailsService implements UserDetailsService {
    
    private UserRepository repo;

    public MyUserDetailsService(UserRepository repo) {
        this.repo = repo;
    }

    @Override
    public UserDetails loadUserByUsername(String userMail) throws UsernameNotFoundException {
        System.out.println("load user by user name "+userMail);
        Optional<User> user = repo.findByUserMail(userMail);
        System.out.println("before if");
        if(user.isPresent()){
            System.out.println(user.get());return new UserPrinciples(user.get());}
        else {
            throw new UsernameNotFoundException("User not found");
        }

    }
}
