package com.gymbuddy.backend.service;

import com.gymbuddy.backend.model.GymDay;

import java.util.List;

public interface GymDayService {
    GymDay addGymDay(GymDay gymDay);
    List<GymDay> getAllGymDays();
    GymDay getGymDayById(Long id);
    GymDay updateGymDay(GymDay gymDay);
    void deleteGymDay(Long id);
}
