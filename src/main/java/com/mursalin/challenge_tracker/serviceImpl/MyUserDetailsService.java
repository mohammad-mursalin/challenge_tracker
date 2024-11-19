package com.mursalin.challenge_tracker.serviceImpl;

import com.mursalin.challenge_tracker.DTO.UserDTO;
import com.mursalin.challenge_tracker.model.User;
import com.mursalin.challenge_tracker.model.UserPrinciples;
import com.mursalin.challenge_tracker.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class MyUserDetailsService implements UserDetailsService {
    
    private UserRepository repo;

    public MyUserDetailsService(UserRepository repo) {
        this.repo = repo;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<UserDTO> user = repo.findUserDTOByEmail(email);

        if(user.isPresent()){
            return new UserPrinciples(user.get());}
        else {
            throw new UsernameNotFoundException("User not found");
        }

    }
}
