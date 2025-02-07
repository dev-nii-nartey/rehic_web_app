package com.rehic.user;

import com.rehic.auth.MyUser;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class DataLoader implements CommandLineRunner {

    @Value("${rehic.admin}")
    private String adminEmail;

    @Value("${rehic.password}")
    private String adminPassword;

    @Value("${rehic.user}")
    private String userEmail;

    @Value("${user.password}")
    private String userPassword;

    private final PasswordEncoder encoder;
    private final UserService userService;
    private final UserRepository userRepo;

    public DataLoader(PasswordEncoder encoder, UserService userService, UserRepository userRepo) {
        this.encoder = encoder;
        this.userService = userService;
        this.userRepo = userRepo;
    }

    @Override
    public void run(String... args) {
        boolean adminExists = userRepo.existsByUsername(adminEmail);
        boolean userExists = userRepo.existsByUsername(userEmail);


        if (!adminExists && !userExists) {
            //ADDING ADMIN
            user(adminEmail, adminPassword,1L,"ADMIN");

            //ADDING USER
            user(userEmail, userPassword,2L,"USER");

        } else {
            System.out.println(userEmail + " already exists");
        }
    }

    private void user(String userEmail, String password,Long Id, String role) {
        MyUser user = new MyUser();
        user.setId(Id);
        user.setUsername(userEmail);
        String ENCODED_PASSWORD = encoder.encode(password);
        user.setPassword(ENCODED_PASSWORD);
        user.setRoles(role);
        user.setEnabled(true);
        String result = userService.addUser(user);
        System.out.println(result);
    }
}