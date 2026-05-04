package com.gymbuddy.backend.repository;

import com.gymbuddy.backend.model.Exercise;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ExerciseRepository extends JpaRepository<Exercise, Long> {
    @Query("SELECT e FROM Exercise e JOIN e.sets s WHERE s.id = :setId")
    Optional<Exercise> findBySetId(@Param("setId") Long setId);
}
