package com.gymbuddy.backend.controller;

import com.gymbuddy.backend.model.GymDay;
import com.gymbuddy.backend.service.GymDayService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/api/gymdays")
public class GymDayController {
    private final GymDayService gymDayService;

    public GymDayController(GymDayService gymDayService) {
        this.gymDayService = gymDayService;
    }

    @GetMapping("")
    public List<GymDay> getAllGymDays() {
        return gymDayService.getAllGymDays();
    }

    @PostMapping("")
    public GymDay saveGymDay(@RequestBody GymDay gymDay) {
        return gymDayService.addGymDay(gymDay);
    }

    @GetMapping("/{id}")
    public GymDay getGymDay(@PathVariable Long id){
        return gymDayService.getGymDayById(id);
    }

    @DeleteMapping("/{id}")
    public void deleteGymDay(@PathVariable Long id) {
        gymDayService.deleteGymDay(id);
    }

    @PostMapping("/{gymDayId}/exercises/{exerciseId}")
    public void addExerciseToGymDay(@PathVariable Long gymDayId, @PathVariable Long exerciseId) {
        gymDayService.addExerciseToGymDay(gymDayId, exerciseId);
    }
}
