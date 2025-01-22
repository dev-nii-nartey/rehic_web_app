package com.rehic.auth;

import com.rehic.security.JwtUtil;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
@AllArgsConstructor
@Getter
@Setter
public class AuthController {


    private AuthenticationManager authenticationManager;

    private JwtUtil jwtUtil;


    private UserDetailsService userDetailsService;

    @PostMapping("/authenticate")
    public String authenticate(@RequestBody AuthRequest authRequest) throws Exception {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authRequest.username(), authRequest.password())
            );

            final UserDetails userDetails = userDetailsService.loadUserByUsername(authRequest.username());
            return jwtUtil.generateToken(userDetails.getUsername());
        } catch (AuthenticationException e) {
            throw new Exception("Invalid username or password", e);
        }
    }
}