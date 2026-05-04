package com.gymbuddy.backend.repository;

import com.gymbuddy.backend.model.ExerciseSet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ExerciseSetRepository extends JpaRepository<ExerciseSet, Long> {
    @Query("SELECT MAX(s.weight) FROM GymDay g JOIN g.exercises e JOIN e.sets s WHERE g.user.id = :userId AND e.exerciseType.id = :exerciseTypeId AND (:excludeSetId IS NULL OR s.id <> :excludeSetId)")
    Double findMaxWeightByUserIdAndExerciseTypeId(@Param("userId") Long userId, @Param("exerciseTypeId") Long exerciseTypeId, @Param("excludeSetId") Long excludeSetId);
}
