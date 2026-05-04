package com.gymbuddy.backend.service;

import com.gymbuddy.backend.model.ExerciseSet;

public interface AchievementService {
    void checkPersonalBest(Long userId, Long exerciseTypeId, String exerciseName, ExerciseSet set);
}
