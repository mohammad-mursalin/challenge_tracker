package com.mursalin.challenge_tracker.serviceImpl;

import com.mursalin.challenge_tracker.model.UserSecretKey;
import com.mursalin.challenge_tracker.repository.SecretKeyRepository;
import com.mursalin.challenge_tracker.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class SaveTokenToDB {

    private SecretKeyRepository secretKeyRepo;

    private UserRepository userRepo;

    @Autowired
    public void setUserRepo(UserRepository userRepo) {
        this.userRepo = userRepo;
    }

    @Autowired
    public void setSecretKeyRepo(SecretKeyRepository secretKeyRepo) {
        this.secretKeyRepo = secretKeyRepo;
    }
    @Transactional
    public void saveKeyToDB(String sk, String jwt, String username) {
        Long userId = userRepo.findUserIdByEmail(username);

        if (secretKeyRepo.existsByUser_UserId(userId)) {
            secretKeyRepo.updateSecretKeyAndJwt(userId, sk, jwt);
        } else {
            UserSecretKey userSecretKey = new UserSecretKey();
            userSecretKey.setUser(userRepo.findByEmail(username));
            userSecretKey.setSecretKey(sk);
            userSecretKey.setJwt(jwt);
            secretKeyRepo.save(userSecretKey);
        }
    }
}
