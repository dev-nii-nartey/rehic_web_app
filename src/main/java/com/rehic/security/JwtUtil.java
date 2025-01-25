package com.rehic.security;

import com.rehic.execptions.JwtConfigurationException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtUtil {

    // Replace this with a secure key in a real application, ideally fetched from environment variables
    @Value("${jwt.secret}" )
    private String secretKey;

    @Value("${jwt.expiration}")
    private long expiration;

    // Generate token with given user name
    public String generateToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("subject", userDetails.getUsername());
        claims.put("authorities", userDetails.getAuthorities());
        return createToken(claims);
    }

    // Create a JWT token with specified claims and subject (username)
    private String createToken(Map<String, Object> claims) {
        return Jwts.builder()
                .claims(claims)
                .subject(claims.get("subject").toString())
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + expiration)) // Token valid for 30 minutes
                .signWith(getSignKey())
                .compact();
    }

    // Get the signing key for JWT token
    private SecretKey getSignKey() {
        try {
            byte[] keyBytes = Decoders.BASE64.decode(secretKey);
            return Keys.hmacShaKeyFor(keyBytes);
        } catch (IllegalArgumentException ex) {
            throw new JwtConfigurationException("Invalid secret key format");
        }
    }

    // Extract the username from the token
    public String extractUsername(String token) {
        return extractAllClaims(token).getSubject();
    }

    // Extract the expiration date from the token
    public Date extractExpiration(String token) {
        return extractAllClaims(token).getExpiration();
    }


    // Extract all claims from the token
    private Claims extractAllClaims(String token) {
        return Jwts.parser()
                .verifyWith(getSignKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    // Check if the token is expired
    private Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    // Validate the token against user details and expiration
    public Boolean validateToken(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    public Map<String, String> parseToken(HttpServletRequest request) {
        final String authorizationHeader = request.getHeader("Authorization");

        final HashMap<String, String> details = new HashMap<>();

        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            String jwt = authorizationHeader.substring(7);
            details.put("username", extractUsername(jwt));
            details.put("jwt", jwt);
            return details;
        }
        return details;
    }
}
