package com.rehic.user;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/rehic")
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
