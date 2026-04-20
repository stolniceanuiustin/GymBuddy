package com.gymbuddy.backend.controller;

import com.gymbuddy.backend.model.WeightLog;
import com.gymbuddy.backend.service.WeightLogService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/weight-logs")
@CrossOrigin(origins = "http://localhost:5173")
@Tag(name = "Weight Tracking", description = "Endpoints for tracking user weight history")
public class WeightLogController {

    private final WeightLogService weightLogService;

    public WeightLogController(WeightLogService weightLogService) {
        this.weightLogService = weightLogService;
    }

    @GetMapping("/user/{userId}")
    @Operation(summary = "Get weight history for a specific user")
    public ResponseEntity<?> getWeightHistory(@PathVariable Long userId, @RequestHeader(value = "X-User-Id", required = false) Long requestorId) {
        return ResponseEntity.ok(weightLogService.getWeightHistory(userId));
    }

    @PostMapping("")
    @Operation(summary = "Log current weight")
    public ResponseEntity<?> addWeightLog( @RequestParam Float weight, @RequestHeader(value = "X-User-Id", required = false) Long requestorId) {
        if (requestorId == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User ID header missing");
        }
        if (weight > 500) {
            return ResponseEntity.badRequest().body("Weight cannot exceed 500 kg.");
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(weightLogService.addWeightLog(requestorId, weight));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a weight log")
    public ResponseEntity<?> deleteWeightLog(@PathVariable Long id) {
        weightLogService.deleteWeightLog(id);
        return ResponseEntity.ok().build();
    }
}
