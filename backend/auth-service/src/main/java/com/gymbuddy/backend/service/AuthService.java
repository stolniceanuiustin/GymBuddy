package com.gymbuddy.backend.service;

import com.gymbuddy.backend.dto.RegisterRequest;
import com.gymbuddy.backend.model.User;

public interface AuthService {
    User login(String username, String password);
    User register(RegisterRequest registerRequest);
    boolean resetPassword(String username, String email, String oldPassword, String newPassword);
}
