package com.gymbuddy.backend.repository;

import com.gymbuddy.backend.model.RoutineTemplate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoutineTemplateRepository extends JpaRepository<RoutineTemplate, Long> {
    List<RoutineTemplate> findByUserId(Long userId);
}
