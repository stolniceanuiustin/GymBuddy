package com.gymbuddy.backend.controller;

import com.gymbuddy.backend.model.ExerciseSet;
import com.gymbuddy.backend.service.ExerciseSetService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/exercisesets")
@CrossOrigin(origins = "http://localhost:5173")
public class ExerciseSetController {
    private final ExerciseSetService exerciseSetService;

    public ExerciseSetController(ExerciseSetService exerciseSetService) {
        this.exerciseSetService = exerciseSetService;
    }

    @GetMapping("")
    public List<ExerciseSet> getAllExerciseSets() {
        return exerciseSetService.getAllExerciseSets();
    }

    @PostMapping("")
    public ExerciseSet addExerciseSet(@RequestBody ExerciseSet exerciseSet) {
        return exerciseSetService.addExerciseSet(exerciseSet);
    }

    @GetMapping("/{id}")
    public ExerciseSet getExerciseSetById(@PathVariable Long id) {
        return exerciseSetService.getExerciseSetById(id);
    }

    @DeleteMapping("/{id}")
    public void deleteExerciseSet(@PathVariable Long id) {
        exerciseSetService.deleteExerciseSet(id);
    }
}
