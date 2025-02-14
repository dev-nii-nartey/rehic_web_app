package com.rehic.user;

import com.rehic.auth.MyUser;

public interface UserService {
    String addUser(MyUser myUser);
    void saveRefreshToken(String email, String refreshToken);
    boolean validateRefreshToken(String username, String refreshToken);
    void invalidateRefreshToken(String username);
}