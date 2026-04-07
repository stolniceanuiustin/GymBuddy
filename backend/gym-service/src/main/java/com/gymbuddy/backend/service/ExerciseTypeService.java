package com.gymbuddy.backend.service;

import com.gymbuddy.backend.model.ExerciseType;
import java.util.List;

public interface ExerciseTypeService {
    ExerciseType addExerciseType(ExerciseType exerciseType);
    List<ExerciseType> getAllExerciseTypes();
    ExerciseType getExerciseTypeById(Long id);
    ExerciseType updateExerciseType(ExerciseType exerciseType);
    void deleteExerciseType(Long id);
}
