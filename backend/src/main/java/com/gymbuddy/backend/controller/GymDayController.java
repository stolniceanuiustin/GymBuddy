package com.gymbuddy.backend.controller;

import com.gymbuddy.backend.model.GymDay;
import com.gymbuddy.backend.service.GymDayService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/api/gymdays")
@CrossOrigin(origins = "http://localhost:5173")
public class GymDayController {
    private final GymDayService gymDayService;

    public GymDayController(GymDayService gymDayService) {
        this.gymDayService = gymDayService;
    }

    @GetMapping("")
    public List<GymDay> getAllGymDays() {
        return gymDayService.getAllGymDays();
    }

    @GetMapping("/user/{userId}")
    public List<GymDay> getGymDaysByUser(@PathVariable Long userId) {
        return gymDayService.getGymDaysByUserId(userId);
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
