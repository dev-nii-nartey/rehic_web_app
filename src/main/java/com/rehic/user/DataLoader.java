package com.rehic.user;

import com.rehic.auth.MyUser;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class DataLoader implements CommandLineRunner {

    @Value("${rehic.admin}")
    private String username;

    @Value("${rehic.password}")
    private String password;

    private final PasswordEncoder encoder;
    private final UserService userService;
    private final UserRepository userRepo; // Inject repository instead of UserDetailsService

    public DataLoader(PasswordEncoder encoder, UserService userService, UserRepository userRepo) {
        this.encoder = encoder;
        this.userService = userService;
        this.userRepo = userRepo; // Use repository for safe checks
    }

    @Override
    public void run(String... args) {
        // Check existence via repository (no exceptions)
        boolean userExists = userRepo.existsByUsername(username);

        if (!userExists) {
            MyUser myUser = new MyUser();
            myUser.setId(1L);
            myUser.setUsername(username);
            String ENCODED_PASSWORD = encoder.encode(password);
            myUser.setPassword(ENCODED_PASSWORD);
            myUser.setRoles("ADMIN");
            myUser.setEnabled(true);

            String result = userService.addUser(myUser);
            System.out.println(result);
        } else {
            System.out.println(username + " already exists");
        }
    }
}