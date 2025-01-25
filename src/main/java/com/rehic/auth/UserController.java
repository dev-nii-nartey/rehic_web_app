package com.rehic.auth;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    @GetMapping("/helloAdmin")
    public String helloAdmin() {
        return "hello admin";
    }

    @GetMapping("/helloUser")
    public String helloUser() {
        return "hello user";
    }

}
