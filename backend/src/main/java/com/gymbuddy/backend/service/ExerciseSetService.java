package com.gymbuddy.backend.service;

import com.gymbuddy.backend.model.ExerciseSet;
import java.util.List;

public interface ExerciseSetService {
    ExerciseSet addExerciseSet(ExerciseSet exerciseSet);
    List<ExerciseSet> getAllExerciseSets();
    ExerciseSet getExerciseSetById(Long id);
    ExerciseSet updateExerciseSet(ExerciseSet exerciseSet);
    void deleteExerciseSet(Long id);
}