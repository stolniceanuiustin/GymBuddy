package com.gymbuddy.backend.service.impl;

import com.gymbuddy.backend.model.User;
import com.gymbuddy.backend.repository.UserRepository;
import com.gymbuddy.backend.service.UserService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class UserServiceImpl implements UserService {

    // this is what dependency injection is,
    // the service is being "served" userRepository by Spring Boot in the background, so no constructor is needed here,
    // kinda makes sense 
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
        User user = userRepository.findById(id);
        if (user != null) {
            return user;
        }
        throw new NoSuchElementException("User with id " + id + " not found");
    }

    @Override
    public User updateUser(User user) {
        return userRepository.updateUser(user);
    }

    @Override
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }
}