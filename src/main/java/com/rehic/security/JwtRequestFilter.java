package com.rehic.security;

import java.io.IOException;
import java.util.Map;
import io.jsonwebtoken.JwtException;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;



import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
@AllArgsConstructor
@Setter
@Getter
public class JwtRequestFilter extends OncePerRequestFilter {

    private UserDetailsService userDetailsService;

    private JwtUtil jwtUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {
        try {
            Map<String, String> tokenInfo = jwtUtil.parseToken(request);
            String username = tokenInfo.get("username");
            String jwt = tokenInfo.get("jwt");


            if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {

                UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);

                if (jwtUtil.validateToken(jwt, userDetails)) {
                    JwtAuthenticationToken jwtAuthenticationToken = new JwtAuthenticationToken(userDetails, userDetails.getAuthorities());
                    jwtAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(jwtAuthenticationToken);
                }
            }
            chain.doFilter(request, response);
        } catch (JwtException | IllegalArgumentException ex) {
            sendErrorResponse(response);
        }
    }



    private void sendErrorResponse(HttpServletResponse response)
            throws IOException {
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.getWriter().write(
                String.format("{\"error\": \"%s\", \"message\": \"%s\"}",
                        HttpStatus.UNAUTHORIZED.getReasonPhrase(), "Invalid token")
        );
    }
}