package com.rehic.auth;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MyUser {
    private Long id;
    private String username;
    private String password;
    private String roles;  //going to be comma seperated
    private boolean enabled;
}