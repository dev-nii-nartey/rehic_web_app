package com.rehic.user;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/rehic")
public class UserController {

    @GetMapping("/helloAdmin")
    public Map<String,String> helloAdmin() {
        return Map.of("testKey","hello admin");
    }

    @GetMapping("/helloUser")
    public  Map<String,String> helloUser() {
        return Map.of("testKey","hello user");
    }

}
