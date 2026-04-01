package com.gymbuddy.backend.controller;

import com.gymbuddy.backend.model.ExerciseType;
import com.gymbuddy.backend.service.ExerciseTypeService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/exercisetypes")
public class ExerciseTypeController {
    private final ExerciseTypeService exerciseTypeService;

    public ExerciseTypeController(ExerciseTypeService exerciseTypeService) {
        this.exerciseTypeService = exerciseTypeService;
    }

    @GetMapping("")
    public List<ExerciseType> getAllExerciseTypes() {
        return exerciseTypeService.getAllExerciseTypes();
    }

    @PostMapping("")
    public ExerciseType addExerciseType(@RequestBody ExerciseType exerciseType) {
        return exerciseTypeService.addExerciseType(exerciseType);
    }

    @GetMapping("/{id}")
    public ExerciseType getExerciseTypeById(@PathVariable Long id) {
        return exerciseTypeService.getExerciseTypeById(id);
    }

    @DeleteMapping("/{id}")
    public void deleteExerciseType(@PathVariable Long id) {
        exerciseTypeService.deleteExerciseType(id);
    }
}
