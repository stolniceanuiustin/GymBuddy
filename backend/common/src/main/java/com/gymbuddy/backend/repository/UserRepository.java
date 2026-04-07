package com.gymbuddy.backend.repository;

import com.gymbuddy.backend.model.User;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class UserRepository {
    private final List<User> users = new ArrayList<>();

    public User save(User user) {
        users.add(user);
        return user;
    }

    public List<User> findAll() {
        return users;
    }

    public User findById(Long id){
        return users.stream().
                filter(user -> user.getId().equals(id)).
                findFirst().orElse(null);
    }

    public User findByUsername(String username) {
        return users.stream()
                .filter(user -> user.getUsername().equals(username))
                .findFirst()
                .orElse(null);
    }

    public User updateUser(User user){
        User oldUser = findById(user.getId());
        if(oldUser != null){
            oldUser.setUsername(user.getUsername());
            oldUser.setEmail(user.getEmail());
            oldUser.setPassword(user.getPassword());
            oldUser.setRole(user.getRole());
            oldUser.setGymDays(user.getGymDays());
            oldUser.setAge(user.getAge());
            oldUser.setHeight(user.getHeight());
            oldUser.setWeight(user.getWeight());
            return oldUser;
        }
        return null;
    }

    public boolean deleteById(Long id){
        return users.removeIf(user -> user.getId().equals(id));
    }
}
