package com.gymbuddy.backend.controller;

import com.gymbuddy.backend.model.GymDay;
import com.gymbuddy.backend.service.GymDayService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/api/gymdays")
@CrossOrigin(origins = "http://localhost:5173")
@Tag(name = "Gym Day", description = "Gym day management APIs")
public class GymDayController {
    private final GymDayService gymDayService;

    public GymDayController(GymDayService gymDayService) {
        this.gymDayService = gymDayService;
    }

    @GetMapping("")
    @Operation(summary = "Get all gym days")
    public List<GymDay> getAllGymDays() {
        return gymDayService.getAllGymDays();
    }

    @GetMapping("/user/{userId}")
    @Operation(summary = "Get gym days by user ID")
    public List<GymDay> getGymDaysByUser(@PathVariable Long userId) {
        return gymDayService.getGymDaysByUserId(userId);
    }

    @PostMapping("")
    @Operation(summary = "Save a gym day")
    public GymDay saveGymDay(@RequestBody GymDay gymDay) {
        return gymDayService.addGymDay(gymDay);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get a gym day by ID")
    public GymDay getGymDay(@PathVariable Long id){
        return gymDayService.getGymDayById(id);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a gym day")
    public void deleteGymDay(@PathVariable Long id) {
        gymDayService.deleteGymDay(id);
    }

    @PostMapping("/{gymDayId}/exercises/{exerciseId}")
    @Operation(summary = "Add an exercise to a gym day")
    public void addExerciseToGymDay(@PathVariable Long gymDayId, @PathVariable Long exerciseId) {
        gymDayService.addExerciseToGymDay(gymDayId, exerciseId);
    }
}
