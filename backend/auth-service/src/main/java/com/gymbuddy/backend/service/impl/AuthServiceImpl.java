package com.gymbuddy.backend.service.impl;

import com.gymbuddy.backend.dto.RegisterRequest;
import com.gymbuddy.backend.model.Role;
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
        return userRepository.findByUsername(username)
                .filter(user -> user.getPassword() != null && user.getPassword().equals(password))
                .orElse(null);
    }

    @Override
    public User register(RegisterRequest registerRequest) {
        if (userRepository.findByUsername(registerRequest.getUsername()).isPresent()) {
            throw new RuntimeException("Username already exists");
        }
        
        User user = User.builder()
                .username(registerRequest.getUsername())
                .email(registerRequest.getEmail())
                .password(registerRequest.getPassword())
                .role(Role.STANDARD_USER)
                .build();
        
        return userRepository.save(user);
    }

    @Override
    public boolean resetPassword(String username, String email, String oldPassword, String newPassword) {
        return userRepository.findByUsername(username)
                .filter(user -> user.getEmail() != null && user.getEmail().equalsIgnoreCase(email))
                .filter(user -> user.getPassword() != null && user.getPassword().equals(oldPassword))
                .map(user -> {
                    user.setPassword(newPassword);
                    userRepository.save(user);
                    return true;
                })
                .orElse(false);
    }
}
