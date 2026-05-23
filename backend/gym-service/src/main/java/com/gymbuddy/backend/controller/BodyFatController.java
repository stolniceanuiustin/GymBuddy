package com.gymbuddy.backend.controller;

import com.gymbuddy.backend.model.BodyFatLog;
import com.gymbuddy.backend.service.BodyFatService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/bodyfat")
@RequiredArgsConstructor
@Tag(name = "Body Fat", description = "Endpoints for body fat prediction and history")
@CrossOrigin(origins = "*")
public class BodyFatController {

    private final BodyFatService bodyFatService;

    @PostMapping("/predict")
    @Operation(summary = "Predict body fat and save the result")
    public ResponseEntity<BodyFatLog> predict(@RequestHeader("X-User-Id") Long userId) {
        return ResponseEntity.ok(bodyFatService.predictAndSave(userId));
    }

    @GetMapping("/history")
    @Operation(summary = "Get body fat history for a user")
    public ResponseEntity<List<BodyFatLog>> getHistory(@RequestHeader("X-User-Id") Long userId) {
        return ResponseEntity.ok(bodyFatService.getHistory(userId));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a body fat log entry")
    public ResponseEntity<Void> deleteLog(@PathVariable Long id) {
        bodyFatService.deleteLog(id);
        return ResponseEntity.noContent().build();
    }
}
