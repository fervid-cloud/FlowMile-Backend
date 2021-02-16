package com.example.demo.security;

import com.example.demo.model.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.time.Instant;
import java.time.LocalDate;
import java.util.Date;
import org.springframework.stereotype.Component;

@Component
public class JWTManager {

    private String jwtSecret = "testUser";

    public  CustomAuthenticationToken verifyToken(String token) {
        try {
            Claims body = Jwts.parser()
                              .setSigningKey(jwtSecret)
                              .parseClaimsJws(token)
                              .getBody();

            CustomAuthenticationToken customAuthenticationToken = new CustomAuthenticationToken();
            customAuthenticationToken.setUsername(body.getSubject());
            return customAuthenticationToken;
        } catch (JwtException | ClassCastException e) {
            return null;
        }
    }

    public String generateToken(User user) {
        Claims claims = Jwts.claims().setSubject(user.getUsername());
        claims.put("userId", user.getUserId() + "");
        return Jwts.builder()
            .setIssuer("tester")
            .setSubject(user.getUsername())
            .setClaims(claims)
            .setIssuedAt(Date.from(Instant.from(LocalDate.now())))
            .setExpiration(Date.from(Instant.from(LocalDate.now().plusDays(2))))
            .signWith(SignatureAlgorithm.HS512, jwtSecret)
            .compact();
    }
}
