package com.gymbuddy.backend.controller;

import com.gymbuddy.backend.dto.ExerciseTypeDTO;
import com.gymbuddy.backend.security.SecurityUtils;
import com.gymbuddy.backend.service.ExerciseTypeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/exercise-types")
@CrossOrigin(origins = "http://localhost:5173")
@Tag(name = "Exercise Type", description = "Exercise catalog management APIs")
public class ExerciseTypeController {
    private final ExerciseTypeService exerciseTypeService;
    private final SecurityUtils securityUtils;

    public ExerciseTypeController(ExerciseTypeService exerciseTypeService, SecurityUtils securityUtils) {
        this.exerciseTypeService = exerciseTypeService;
        this.securityUtils = securityUtils;
    }

    @PostMapping("")
    @Operation(summary = "Add a new exercise type (Admin Only)")
    public ResponseEntity<?> addExerciseType(@RequestBody ExerciseTypeDTO exerciseTypeDTO, @RequestHeader(value = "X-User-Id", required = false) Long requestorId) {
        if (!securityUtils.isAdministrator(requestorId)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Access Denied: Administrator role required.");
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(exerciseTypeService.addExerciseType(exerciseTypeDTO));
    }

    @GetMapping("")
    @Operation(summary = "Get all exercise types")
    public List<ExerciseTypeDTO> getAllExerciseTypes() {
        return exerciseTypeService.getAllExerciseTypes();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get an exercise type by ID")
    public ExerciseTypeDTO getExerciseTypeById(@PathVariable Long id) {
        return exerciseTypeService.getExerciseTypeById(id);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update an exercise type (Admin Only)")
    public ResponseEntity<?> updateExerciseType(@PathVariable Long id, @RequestBody ExerciseTypeDTO exerciseTypeDTO, @RequestHeader(value = "X-User-Id", required = false) Long requestorId) {
        if (!securityUtils.isAdministrator(requestorId)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Access Denied: Administrator role required.");
        }
        exerciseTypeDTO.setId(id);
        return ResponseEntity.ok(exerciseTypeService.updateExerciseType(exerciseTypeDTO));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete an exercise type (Admin Only)")
    public ResponseEntity<?> deleteUser(@PathVariable Long id, @RequestHeader(value = "X-User-Id", required = false) Long requestorId) {
        if (!securityUtils.isAdministrator(requestorId)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Access Denied: Administrator role required.");
        }
        exerciseTypeService.deleteExerciseType(id);
        return ResponseEntity.ok().build();
    }
}
