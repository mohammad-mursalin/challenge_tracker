package com.mursalin.challenge_tracker.serviceImpl;

import com.mursalin.challenge_tracker.repository.SecretKeyRepository;
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
import java.security.NoSuchAlgorithmException;
import java.util.*;
import java.util.function.Function;

@Service
public class JwtService {

    private SaveTokenToDB saveTokenToDB;

    @Autowired
    public void setSaveTokenToDB(SaveTokenToDB saveTokenToDB) {
        this.saveTokenToDB = saveTokenToDB;
    }

    private String sk = "";

    private SecretKeyRepository secretKeyRepo;

    @Autowired
    public void setSecretKeyRepo(SecretKeyRepository secretKeyRepo) {
        this.secretKeyRepo = secretKeyRepo;
    }

    public String generateToken(@NonNull String username) {
        Map<String, Object> claims = new HashMap<>();

        String jwt = Jwts.builder()
                .claims()
                .add(claims)
                .subject(username)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + 5*60*1000))
                .and()
                .signWith(getKey())
                .compact();

        saveTokenToDB.saveKeyToDB(sk, jwt, username);
        return jwt;
    }

    private SecretKey getKey() {
        try {
            KeyGenerator keyGenerator = KeyGenerator.getInstance("HmacSHA256");
            SecretKey secretKey = keyGenerator.generateKey();
            sk = Base64.getEncoder().encodeToString(secretKey.getEncoded());
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }

        byte[] keyByte = Decoders.BASE64.decode(sk);
        return Keys.hmacShaKeyFor(keyByte);
    }

    public SecretKey getSecretKeyFromDB(String token) {
        String skFromDB = secretKeyRepo.findSecretKeyByJwt(token);
        byte[] keyByte = Decoders.BASE64.decode(skFromDB);
        return Keys.hmacShaKeyFor(keyByte);
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
