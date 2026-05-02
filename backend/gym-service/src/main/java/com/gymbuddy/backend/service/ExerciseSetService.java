package com.gymbuddy.backend.service;

import com.gymbuddy.backend.dto.ExerciseSetDTO;
import java.util.List;

public interface ExerciseSetService {
    ExerciseSetDTO addExerciseSet(ExerciseSetDTO exerciseSetDTO);
    List<ExerciseSetDTO> getAllExerciseSets();
    ExerciseSetDTO getExerciseSetById(Long id);
    ExerciseSetDTO updateExerciseSet(ExerciseSetDTO exerciseSetDTO);
    void deleteExerciseSet(Long id);
}