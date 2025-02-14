package com.rehic.auth;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MyUser {
    private Long id;
    private String username;
    private String password;
    private String roles;  //going to be comma seperated
    private boolean enabled;
    private String refreshToken;
}