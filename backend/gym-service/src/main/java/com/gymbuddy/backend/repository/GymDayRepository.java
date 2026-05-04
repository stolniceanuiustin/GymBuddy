package com.gymbuddy.backend.repository;

import com.gymbuddy.backend.model.GymDay;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface GymDayRepository extends JpaRepository<GymDay, Long> {
    List<GymDay> findByUserId(Long userId);

    @Query("SELECT g FROM GymDay g JOIN g.exercises e WHERE e.id = :exerciseId")
    Optional<GymDay> findByExerciseId(@Param("exerciseId") Long exerciseId);
}
