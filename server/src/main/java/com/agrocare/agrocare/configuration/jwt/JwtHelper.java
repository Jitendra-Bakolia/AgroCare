package com.agrocare.agrocare.configuration.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Component
public class JwtHelper {
    private String JWT_TOKEN = "aPb2FD1gHL3mNoQ5rUtV7wX1zZ4b8eEhJm2PqRuT5wYx3z1b5g8kC8yB2w7D3sF4gU6jX3n4g6K9pMq2tW7zZ6q5mPq4tW6zW6zS6tY0x3JcVfYbRn2e5aPdSgUjXn4eHr7yTbEeHaKpMq4t7yW0z3mQd2dPf7vFg3h6J9cAsDfGhJkLp3mBvNzBc1z3XCXCDFRFVGTBHNJMKLKJHGFDSAZXCVBNMASDFGHJKLQWERTYUIOPPqRuT5wYx3z1b5g8kC8yB2w7D3sF4gU6jX3n4g6K9pMq2tW7zZ6q5mPq4tW6zW6zS6tY0x3JcVfYbRn2e5aPdSgUjXn4eHr7yTbEeHaKpMq4t7yW0z3mQd2dPf7vFg3h6J9cAsDfGhJkLp3mBvNzBc1z3XCXCDFRFVGTBHNJMKLKJHGFDSAZXCVBNMASDFGHJKLQWERTYUIOP";

    public static final long JWT_TOKEN_VALIDITY = 5 * 60 * 60;
    private final Key secretKey = Keys.hmacShaKeyFor(JWT_TOKEN.getBytes());

    // Retrieve username from JWT token
    public String getUsernameFromToken(String token) {
        return getClaimFromToken(token, Claims::getSubject);
    }

    // Retrieve expiration date from JWT token
    public Date getExpirationDateFromToken(String token) {
        return getClaimFromToken(token, Claims::getExpiration);
    }

    // Generic method to retrieve claims from JWT token using a resolver function
    public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }

    // Parse JWT token for retrieveing any information from token
    private Claims getAllClaimsFromToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build().parseClaimsJws(token)
                .getBody();
    }

    // Check if the token has expired
    private Boolean isTokenExpired(String token) {
        final Date expiration = getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }

    // Generate JWT token for a user
    public String generateToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        return doGenerateToken(claims, userDetails.getUsername());
    }

    // Create JWT token with specified claims and subject (user's username)
    private String doGenerateToken(Map<String, Object> claims, String subject) {

        return Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + JWT_TOKEN_VALIDITY * 1000))
                .signWith(secretKey, SignatureAlgorithm.HS512)
                .compact();
    }

    // Validate JWT token against user details
    public Boolean validateToken(String token, UserDetails userDetails) {
        final String username = getUsernameFromToken(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

}