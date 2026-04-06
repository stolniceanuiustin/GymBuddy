package com.gymbuddy.backend.controller;

import com.gymbuddy.backend.model.Exercise;
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
    public List<Exercise> getAllExercises() {
        return exerciseService.getAllExercises();
    }

    @PostMapping("")
    @Operation(summary = "Add a new exercise")
    public Exercise addExercise(@RequestBody Exercise exercise) {
        return exerciseService.addExercise(exercise);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get an exercise by ID")
    public Exercise getExerciseById(@PathVariable Long id) {
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
