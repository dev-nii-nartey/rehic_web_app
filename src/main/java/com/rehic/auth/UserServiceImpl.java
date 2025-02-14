package com.rehic.auth;

import com.rehic.security.JwtUtil;
import com.rehic.user.UserRepository;
import com.rehic.user.UserService;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@Service
@Getter
@Setter
@AllArgsConstructor
public class UserServiceImpl implements UserDetailsService, UserService {

    private final JwtUtil jwtUtil;
    private UserRepository userRepo;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        MyUser myUser = userRepo.findByUsername(email).orElseThrow(() -> new UsernameNotFoundException(email));
        // Log the roles for debugging

        return User.builder()
                .username(myUser.getUsername())
                .password(myUser.getPassword())
                .roles(myUser.getRoles().split(",")) // Split comma-separated roles
                .disabled(!myUser.isEnabled())
                .build();
    }

    @Override
    public String addUser(MyUser myUser) {
        userRepo.save(myUser);
        return "Added User Successfully";
    }

    @Override
    public void saveRefreshToken(String username, String refreshToken) {
        MyUser user = userRepo.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(username));
        user.setRefreshToken(refreshToken);
        userRepo.save(user);
    }

    @Override
    public boolean validateRefreshToken( String username, String refreshToken) {
        MyUser user = userRepo.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(username));

       return user.getRefreshToken().equals(refreshToken) &&
               jwtUtil.isValidRefreshToken(refreshToken,user.getUsername());

    }

    @Override
    public void invalidateRefreshToken(String username) {
        MyUser user = userRepo.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(username));
        user.setRefreshToken(null);
        userRepo.save(user);
    }

}
