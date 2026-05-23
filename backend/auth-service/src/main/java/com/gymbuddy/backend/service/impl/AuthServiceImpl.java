package com.gymbuddy.backend.service.impl;

import com.gymbuddy.backend.dto.RegisterRequest;
import com.gymbuddy.backend.model.Role;
import com.gymbuddy.backend.model.User;
import com.gymbuddy.backend.repository.UserRepository;
import com.gymbuddy.backend.service.AuthService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public User login(String username, String password) {
        return userRepository.findByUsername(username)
                .filter(user -> user.getPassword() != null && passwordEncoder.matches(password, user.getPassword()))
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
                .password(passwordEncoder.encode(registerRequest.getPassword()))
                .role(Role.STANDARD_USER)
                .build();
        
        return userRepository.save(user);
    }

    @Override
    public boolean resetPassword(String username, String email, String oldPassword, String newPassword) {
        return userRepository.findByUsername(username)
                .filter(user -> user.getEmail() != null && user.getEmail().equalsIgnoreCase(email))
                .filter(user -> user.getPassword() != null && passwordEncoder.matches(oldPassword, user.getPassword()))
                .map(user -> {
                    user.setPassword(passwordEncoder.encode(newPassword));
                    userRepository.save(user);
                    return true;
                })
                .orElse(false);
    }
}
