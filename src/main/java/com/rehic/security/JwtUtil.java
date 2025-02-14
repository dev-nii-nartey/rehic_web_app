package com.rehic.security;

import com.rehic.execptions.JwtConfigurationException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
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


    @Value("${access.token.expiration}")
    private long accessTokenExpiration;

    @Value("${refresh.token.expiration}")
    private long refreshTokenExpiration;


    //Generate RefreshToken
    public String generateRefreshToken(UserDetails user) {
        return generateToken(user,refreshTokenExpiration, "refresh");
    }


    //Generate accessToken
    public String generateAccessToken(UserDetails user) {
        return generateToken(user,accessTokenExpiration, "access");
    }

    // Generate token with given user name
    private String generateToken(UserDetails userDetails, Long expiration, String tokenType) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("subject", userDetails.getUsername());
        claims.put("authorities", userDetails.getAuthorities());
        claims.put("type", tokenType);
        return createToken(claims,expiration);
    }

    // Create a JWT token with specified claims and subject (email)
    private String createToken(Map<String, Object> claims, Long expiration) {
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

    // Extract the email from the token
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
    public boolean isValidAccessToken(String user, String accessToken) {
        try {
            return validateToken(accessToken, user,"access");
        }catch (Exception e) {
            return false;
        }
    }

    public boolean isValidRefreshToken(String refreshToken, String user) {
        try {
            return  validateToken(refreshToken, user,"refresh");
        } catch (Exception e) {
            return false;
        }
    }

    private Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    // Validate the token against user details and expiration
    private Boolean validateToken(String token, String userDetails, String tokenType) {
        final String username = extractUsername(token);
        return (username.equals(userDetails) &&
                !isTokenExpired(token) &&
                extractAllClaims(token).containsKey(tokenType)
        );
    }

    public Map<String, String> parseToken(HttpServletRequest request) {
        final String authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);

        final HashMap<String, String> details = new HashMap<>();

        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            String jwt = authorizationHeader.substring(7);
            details.put("email", extractUsername(jwt));
            details.put("jwt", jwt);
            return details;
        }
        return details;
    }
}
