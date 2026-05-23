package com.gymbuddy.backend.repository;

import com.gymbuddy.backend.model.BodyFatLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BodyFatLogRepository extends JpaRepository<BodyFatLog, Long> {
    List<BodyFatLog> findByUserIdOrderByDateDesc(Long userId);
}
