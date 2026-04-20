package com.gymbuddy.backend.security;

import com.gymbuddy.backend.model.Role;
import com.gymbuddy.backend.model.User;
import com.gymbuddy.backend.repository.UserRepository;
import org.springframework.stereotype.Component;

@Component
public class SecurityUtils {

    private final UserRepository userRepository;

    public SecurityUtils(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public boolean isAdministrator(Long userId) {
        if (userId == null) return false;
        return userRepository.findById(userId)
                .map(user -> user.getRole() == Role.ADMINISTRATOR)
                .orElse(false);
    }
}
