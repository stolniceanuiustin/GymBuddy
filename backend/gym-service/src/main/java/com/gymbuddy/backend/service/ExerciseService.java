package com.gymbuddy.backend.service;

import com.gymbuddy.backend.dto.ExerciseDTO;
import java.util.List;

public interface ExerciseService {
    ExerciseDTO addExercise(ExerciseDTO exerciseDTO);
    List<ExerciseDTO> getAllExercises();
    ExerciseDTO getExerciseById(Long id);
    ExerciseDTO updateExercise(ExerciseDTO exerciseDTO);
    void deleteExercise(Long id);
    void addSetToExercise(Long exerciseId, Long setId);
}