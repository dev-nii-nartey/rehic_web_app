package com.rehic.auth;

import com.rehic.security.JwtUtil;
import com.rehic.user.UserService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;



@RestController
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@RequestMapping("/api/v1/rehic")
public class AuthController {


    private AuthenticationManager authenticationManager;
    private JwtUtil jwtUtil;
    private UserDetailsService userDetailsService;
    private UserService userService;

    @PostMapping("/login")
    public AuthResponse authenticate(@RequestBody AuthRequest authRequest) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authRequest.email(), authRequest.password())
            );
            final UserDetails userDetails = userDetailsService.loadUserByUsername(authRequest.email());
            String refreshToken = jwtUtil.generateRefreshToken(userDetails);
            String accessToken = jwtUtil.generateAccessToken(userDetails);
            userService.saveRefreshToken(authRequest.email(), refreshToken);
            return new AuthResponse(refreshToken, accessToken);
        } catch (AuthenticationException e) {
            throw new AccessDeniedException(e.getMessage());

        }
    }


    @PostMapping("/refresh_token")
    public ResponseEntity<?> refreshToken(HttpServletRequest req) {
        try {
            String header = req.getHeader(HttpHeaders.AUTHORIZATION);

            if (header == null || !header.startsWith("Bearer ")) {
                throw new AccessDeniedException("Invalid refresh token");
            }
            String refreshToken = header.substring(7);
            String username = jwtUtil.extractUsername(refreshToken);
             if(!userService.validateRefreshToken(username, refreshToken)){
                 return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            }
            userService.invalidateRefreshToken(username);
            UserDetails user = userDetailsService.loadUserByUsername(username);
            String newAccessToken = jwtUtil.generateAccessToken(user);
            String newRefreshToken = jwtUtil.generateRefreshToken(user);
            userService.saveRefreshToken(username, newRefreshToken);

            return ResponseEntity.ok(new AuthResponse(newRefreshToken, newAccessToken));
        } catch (Exception e) {
            throw new AccessDeniedException(e.getMessage());
        }
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout(HttpServletRequest request) {
        String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7);
            String username = jwtUtil.extractUsername(token);
            userService.saveRefreshToken(username, null);
        }
        return ResponseEntity.ok().build();
    }
}