package com.gymbuddy.backend.repository;

import com.gymbuddy.backend.model.GymDay;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GymDayRepository extends JpaRepository<GymDay, Long> {
    List<GymDay> findByUserId(Long userId);
}
