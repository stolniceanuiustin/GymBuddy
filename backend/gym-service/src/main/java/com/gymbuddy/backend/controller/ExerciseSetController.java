package com.gymbuddy.backend.controller;

import com.gymbuddy.backend.dto.ExerciseSetDTO;
import com.gymbuddy.backend.service.ExerciseSetService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/exercisesets")
@CrossOrigin(origins = "http://localhost:5173")
@Tag(name = "Exercise Set", description = "Exercise set management APIs")
public class ExerciseSetController {
    private final ExerciseSetService exerciseSetService;

    public ExerciseSetController(ExerciseSetService exerciseSetService) {
        this.exerciseSetService = exerciseSetService;
    }

    @GetMapping("")
    @Operation(summary = "Get all exercise sets")
    public List<ExerciseSetDTO> getAllExerciseSets() {
        return exerciseSetService.getAllExerciseSets();
    }

    @PostMapping("")
    @Operation(summary = "Add a new exercise set")
    public ExerciseSetDTO addExerciseSet(@RequestBody ExerciseSetDTO exerciseSetDTO) {
        return exerciseSetService.addExerciseSet(exerciseSetDTO);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get an exercise set by ID")
    public ExerciseSetDTO getExerciseSetById(@PathVariable Long id) {
        return exerciseSetService.getExerciseSetById(id);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete an exercise set")
    public void deleteExerciseSet(@PathVariable Long id) {
        exerciseSetService.deleteExerciseSet(id);
    }
}
