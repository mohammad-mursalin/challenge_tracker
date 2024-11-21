package com.mursalin.challenge_tracker.serviceImpl;

import com.mursalin.challenge_tracker.model.UserSecretKey;
import com.mursalin.challenge_tracker.repository.SecretKeyRepository;
import com.mursalin.challenge_tracker.repository.UserRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtService {

    //private final String secretKey = "TaK+HaV^uvCHEFsEVfypW#7g9^k*Z8$V";

    private String sk = "";

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

    public void stringSecretKey(String email) {
        try {
            KeyGenerator keyGenerator = KeyGenerator.getInstance("HmacSHA256");
            SecretKey secretKey = keyGenerator.generateKey();
            sk = Base64.getEncoder().encodeToString(secretKey.getEncoded());
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
        UserSecretKey userSecretKey = new UserSecretKey();
        userSecretKey.setUser(userRepo.findByEmail(email));
        userSecretKey.setSecretKey(sk);
        secretKeyRepo.save(userSecretKey);
    }

    public String generateToken(@NonNull String username) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("userId", userRepo.findUserIdByEmail(username));

        return Jwts.builder()
                .claims()
                .add(claims)
                .subject(username)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + 5*60*1000))
                .and()
                .signWith(getKey(username))
                .compact();
    }

    private SecretKey getKey(String username) {
        stringSecretKey(username);
        byte[] keyByte = Decoders.BASE64.decode(sk);
        return Keys.hmacShaKeyFor(keyByte);
    }

    private SecretKey getSecretKeyFromDB(String token) {
        long userId = extractUserId(token);
        String skFromDB = secretKeyRepo.findSecretKeyByUser_UserId(userId);
        byte[] keyByte = Decoders.BASE64.decode(skFromDB);
        System.out.println("getSecretKeyFrom db");
        System.out.println(keyByte);
        return Keys.hmacShaKeyFor(keyByte);
    }

    public Long extractUserId(String token) {
        System.out.println("Extract user id");
        return Jwts.parser()
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .get("userId", Long.class);
    }


    public String extractUsername(String token) {

        return extractClaim(token, Claims::getSubject);
    }

    private <T> T extractClaim(String token, Function<Claims, T> claimResolver) {
        final Claims claims = extractAllClaims(token);
        return claimResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parser()
                .verifyWith(getSecretKeyFromDB(token))
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    public boolean validateToken(String token, UserDetails userDetails) {
        final String userName = extractUsername(token);
        return (userName.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }
}
