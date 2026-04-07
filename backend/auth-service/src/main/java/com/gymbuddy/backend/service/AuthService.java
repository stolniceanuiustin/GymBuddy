package com.gymbuddy.backend.service;

import com.gymbuddy.backend.model.User;

public interface AuthService {
    User login(String username, String password);
    boolean resetPassword(String username, String email, String newPassword);
}
