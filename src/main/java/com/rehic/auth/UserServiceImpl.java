package com.rehic.auth;

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
public class UserServiceImpl implements UserDetailsService,UserService {

    private UserRepository userRepo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        MyUser myUser = userRepo.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException(username));
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

}
