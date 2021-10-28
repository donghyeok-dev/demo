package com.example.oauthjwt.config.oauth2.token;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Date;

@Service
public class OAuth2TokenService {
    @Value("${jwt.secret}")
    private String secretKey;

    @Value("${jwt.tokenExpiration}")
    private long tokenExpiration;

    @Value("${jwt.refreshTokenExpiration}")
    private long refreshTokenExpiration;


    @PostConstruct
    protected void init() {
        this.secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes(StandardCharsets.UTF_8));
    }

    public OAuth2Token generateToken(String uid, String role) {


        Claims claims = Jwts.claims().setSubject(uid);
        claims.put("role", role);

        Date now = new Date();
        String token = Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() * tokenExpiration))
                .signWith(SignatureAlgorithm.HS256, this.secretKey)
                .compact();
        String refreshToken = Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() * refreshTokenExpiration))
                .signWith(SignatureAlgorithm.HS256, this.secretKey)
                .compact();

        return OAuth2Token.builder()
                .token(token)
                .refreshToken(refreshToken)
                .build();
    }

    public boolean validationToken(String token) {
        try {
            Jws<Claims> claims = Jwts.parser()
                    .setSigningKey(this.secretKey)
                    .parseClaimsJws(token);

            return claims.getBody()
                    .getExpiration()
                    .after(new Date());
        } catch(Exception e) {
            return false;
        }
    }
}
