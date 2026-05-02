package com.gymbuddy.backend.service;

import com.gymbuddy.backend.dto.GymDayDTO;

import java.util.List;

public interface GymDayService {
    GymDayDTO addGymDay(GymDayDTO gymDayDTO);
    List<GymDayDTO> getAllGymDays();
    GymDayDTO getGymDayById(Long id);
    GymDayDTO updateGymDay(GymDayDTO gymDayDTO);
    List<GymDayDTO> getGymDaysByUserId(Long userId);
    void deleteGymDay(Long id);
    void addExerciseToGymDay(Long gymDayId, Long exerciseId);
}
