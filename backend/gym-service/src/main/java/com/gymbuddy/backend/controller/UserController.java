package com.gymbuddy.backend.controller;

import com.gymbuddy.backend.model.User;
import com.gymbuddy.backend.security.SecurityUtils;
import com.gymbuddy.backend.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "http://localhost:5173")
@Tag(name = "User", description = "User management APIs")
public class UserController {
    private final UserService userService;
    private final SecurityUtils securityUtils;

    public UserController(UserService userService, SecurityUtils securityUtils) {
        this.userService = userService;
        this.securityUtils = securityUtils;
    }

    @GetMapping("")
    @Operation(summary = "Get all users (Admin Only)")
    public ResponseEntity<?> getAllUsers(@RequestHeader(value = "X-User-Id", required = false) Long requestorId) {
        if (!securityUtils.isAdministrator(requestorId)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Access Denied: Administrator role required.");
        }
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @PostMapping("")
    @Operation(summary = "Add a new user")
    public User addUser(@RequestBody User user) {
        return userService.addUser(user);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get a user by ID")
    public User getUserById(@PathVariable Long id) {
        return userService.getUserById(id);
    }

    @PutMapping("/{id}/profile")
    @Operation(summary = "Update user personal data (Weight, Height, Age)")
    public ResponseEntity<?> updateProfile(
            @PathVariable Long id,
            @RequestBody User profileData,
            @RequestHeader(value = "X-User-Id", required = false) Long requestorId) {

        if (profileData.getWeight() != null && profileData.getWeight() > 500) {
            return ResponseEntity.badRequest().body("Weight cannot exceed 500 kg.");
        }

        User user = userService.getUserById(id);
        if (user == null) {
            return ResponseEntity.notFound().build();
        }
        
        user.setWeight(profileData.getWeight());
        user.setHeight(profileData.getHeight());
        user.setAge(profileData.getAge());
        return ResponseEntity.ok(userService.updateUser(user));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update a user")
    public User updateUser(@PathVariable Long id, @RequestBody User user) {
        user.setId(id);
        return userService.updateUser(user);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a user (Admin Only)")
    public ResponseEntity<?> deleteUser(@PathVariable Long id, @RequestHeader(value = "X-User-Id", required = false) Long requestorId) {
        if (!securityUtils.isAdministrator(requestorId)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Access Denied: Administrator role required.");
        }
        userService.deleteUser(id);
        return ResponseEntity.ok().build();
    }
}
