package com.gymbuddy.backend.repository;

import com.gymbuddy.backend.model.GymDay;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class GymDayRepository {
    private final List<GymDay> gymDays = new ArrayList<>();

    public GymDay save(GymDay gymDay) {
        gymDays.add(gymDay);
        return gymDay;
    }

    public List<GymDay> findAll() {
        return gymDays;
    }

    public GymDay findById(Long id){
        return gymDays.stream().
                filter(gymDay -> gymDay.getId().equals(id)).
                findFirst().orElse(null);
    }

    public List<GymDay> findByUserId(Long userId) {
        return gymDays.stream()
                .filter(gd -> gd.getUser() != null && gd.getUser().getId().equals(userId))
                .toList();
    }

    public GymDay updateGymDay(GymDay gymDay){
        GymDay oldGymDay = findById(gymDay.getId());
        if(oldGymDay != null){
            oldGymDay.setDate(gymDay.getDate());
            oldGymDay.setName(gymDay.getName());
            oldGymDay.setUser(gymDay.getUser());
            oldGymDay.setNotes(gymDay.getNotes());
            oldGymDay.setSleepQuality(gymDay.getSleepQuality());
            oldGymDay.setEnergyLevel(gymDay.getEnergyLevel());
            return oldGymDay;
        }
        return null;
    }

    public boolean deleteById(Long id){
        return gymDays.removeIf(gymDay -> gymDay.getId().equals(id));
    }
}