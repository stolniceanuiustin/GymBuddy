package com.gymbuddy.backend.service;

import com.gymbuddy.backend.dto.ExerciseTypeDTO;
import java.util.List;

public interface ExerciseTypeService {
    ExerciseTypeDTO addExerciseType(ExerciseTypeDTO exerciseTypeDTO);
    List<ExerciseTypeDTO> getAllExerciseTypes();
    ExerciseTypeDTO getExerciseTypeById(Long id);
    ExerciseTypeDTO updateExerciseType(ExerciseTypeDTO exerciseTypeDTO);
    void deleteExerciseType(Long id);
}
