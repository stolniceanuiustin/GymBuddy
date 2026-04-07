package com.gymbuddy.backend.service.impl;

import com.gymbuddy.backend.model.User;
import com.gymbuddy.backend.repository.UserRepository;
import com.gymbuddy.backend.service.AuthService;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;

    public AuthServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User login(String username, String password) {
        User user = userRepository.findByUsername(username);
        if (user != null && user.getPassword() != null && user.getPassword().equals(password)) {
            return user;
        }
        return null;
    }

    @Override
    public boolean resetPassword(String username, String email, String newPassword) {
        User user = userRepository.findByUsername(username);
        if (user != null && user.getEmail() != null && user.getEmail().equalsIgnoreCase(email)) {
            user.setPassword(newPassword);
            userRepository.updateUser(user);
            return true;
        }
        return false;
    }
}
