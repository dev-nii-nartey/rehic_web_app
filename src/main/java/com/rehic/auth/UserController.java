package com.rehic.auth;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
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
