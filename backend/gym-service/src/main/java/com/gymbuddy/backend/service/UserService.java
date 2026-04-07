package com.gymbuddy.backend.service;

import com.gymbuddy.backend.model.User;
import java.util.List;

public interface UserService {
    User addUser(User user);
    List<User> getAllUsers();
    User getUserById(Long id);
    User updateUser(User user);
    void deleteUser(Long id);
}