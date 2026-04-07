package com.gymbuddy.backend.controller;

import com.gymbuddy.backend.model.User;
import com.gymbuddy.backend.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "http://localhost:5173")
@Tag(name = "Authentication", description = "Login and password management APIs")
public class AuthController {
    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    @Operation(summary = "Login a user")
    public ResponseEntity<User> login(@RequestParam String username, @RequestParam String password) {
        User user = authService.login(username, password);
        if (user != null) {
            return ResponseEntity.ok(user);
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    @PostMapping("/forgot-password")
    @Operation(summary = "Reset user password")
    public ResponseEntity<String> forgotPassword(@RequestParam String username, @RequestParam String email, @RequestParam String newPassword) {
        boolean success = authService.resetPassword(username, email, newPassword);
        if (success) {
            return ResponseEntity.ok("Password reset successfully");
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid username or email");
    }
}
