package com.gymbuddy.backend.service.impl;

import com.gymbuddy.backend.exception.ResourceNotFoundException;
import com.gymbuddy.backend.model.User;
import com.gymbuddy.backend.repository.UserRepository;
import com.gymbuddy.backend.service.UserService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User addUser(User user) {
        return userRepository.save(user);
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public User getUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", id));
    }

    @Override
    public User updateUser(User user) {
        if (!userRepository.existsById(user.getId())) {
             throw new ResourceNotFoundException("User", "id", user.getId());
        }
        return userRepository.save(user);
    }

    @Override
    public void deleteUser(Long id) {
        if (!userRepository.existsById(id)) {
            throw new ResourceNotFoundException("User", "id", id);
        }
        userRepository.deleteById(id);
    }
}
