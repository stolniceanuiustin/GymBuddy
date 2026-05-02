package com.gymbuddy.backend.controller;

import com.gymbuddy.backend.dto.ExerciseDTO;
import com.gymbuddy.backend.service.ExerciseService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/exercises")
@CrossOrigin(origins = "http://localhost:5173")
@Tag(name = "Exercise", description = "Exercise management APIs")
public class ExerciseController {
    private final ExerciseService exerciseService;

    public ExerciseController(ExerciseService exerciseService) {
        this.exerciseService = exerciseService;
    }

    @GetMapping("")
    @Operation(summary = "Get all exercises")
    public List<ExerciseDTO> getAllExercises() {
        return exerciseService.getAllExercises();
    }

    @PostMapping("")
    @Operation(summary = "Add a new exercise")
    public ExerciseDTO addExercise(@RequestBody ExerciseDTO exerciseDTO) {
        return exerciseService.addExercise(exerciseDTO);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get an exercise by ID")
    public ExerciseDTO getExerciseById(@PathVariable Long id) {
        return exerciseService.getExerciseById(id);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete an exercise")
    public void deleteExercise(@PathVariable Long id) {
        exerciseService.deleteExercise(id);
    }

    @PostMapping("/{exerciseId}/sets/{setId}")
    @Operation(summary = "Add a set to an exercise")
    public void addSetToExercise(@PathVariable Long exerciseId, @PathVariable Long setId) {
        exerciseService.addSetToExercise(exerciseId, setId);
    }
}
