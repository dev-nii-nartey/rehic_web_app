package com.rehic.auth;

import com.rehic.security.JwtUtil;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
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

    @PostMapping("/login")
    public AuthResponse authenticate(@RequestBody AuthRequest authRequest)  {
        try {
         authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authRequest.email(), authRequest.password())
            );
            final UserDetails userDetails = userDetailsService.loadUserByUsername(authRequest.email());
            String refreshToken = jwtUtil.generateRefreshToken(userDetails);
            String accessToken = jwtUtil.generateAccessToken(userDetails);
            return new AuthResponse(refreshToken,accessToken);
        }
        catch (AuthenticationException e) {
            throw new AccessDeniedException(e.getMessage());
        }
    }
}