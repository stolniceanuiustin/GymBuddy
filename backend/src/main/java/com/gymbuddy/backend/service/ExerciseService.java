package com.gymbuddy.backend.service;

import com.gymbuddy.backend.model.Exercise;
import java.util.List;

public interface ExerciseService {
    Exercise addExercise(Exercise exercise);
    List<Exercise> getAllExercises();
    Exercise getExerciseById(Long id);
    Exercise updateExercise(Exercise exercise);
    void deleteExercise(Long id);
    void addSetToExercise(Long exerciseId, Long setId);
    }