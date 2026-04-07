package com.gymbuddy.backend.controller;

import com.gymbuddy.backend.model.ExerciseType;
import com.gymbuddy.backend.service.ExerciseTypeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/exercisetypes")
@CrossOrigin(origins = "http://localhost:5173")
@Tag(name = "Exercise Type", description = "Exercise type management APIs")
public class ExerciseTypeController {
    private final ExerciseTypeService exerciseTypeService;

    public ExerciseTypeController(ExerciseTypeService exerciseTypeService) {
        this.exerciseTypeService = exerciseTypeService;
    }

    @GetMapping("")
    @Operation(summary = "Get all exercise types")
    public List<ExerciseType> getAllExerciseTypes() {
        return exerciseTypeService.getAllExerciseTypes();
    }

    @PostMapping("")
    @Operation(summary = "Add a new exercise type")
    public ExerciseType addExerciseType(@RequestBody ExerciseType exerciseType) {
        return exerciseTypeService.addExerciseType(exerciseType);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get an exercise type by ID")
    public ExerciseType getExerciseTypeById(@PathVariable Long id) {
        return exerciseTypeService.getExerciseTypeById(id);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete an exercise type")
    public void deleteExerciseType(@PathVariable Long id) {
        exerciseTypeService.deleteExerciseType(id);
    }
}
