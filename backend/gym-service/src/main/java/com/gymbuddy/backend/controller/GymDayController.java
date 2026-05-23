package com.gymbuddy.backend.controller;

import com.gymbuddy.backend.dto.GymDayDTO;
import com.gymbuddy.backend.dto.WorkoutExportDTO;
import com.gymbuddy.backend.exporter.FileExporter;
import com.gymbuddy.backend.exporter.XMLFileExporter;
import com.gymbuddy.backend.service.GymDayService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    public List<GymDayDTO> getAllGymDays() {
        return gymDayService.getAllGymDays();
    }

    @GetMapping("/user/{userId}")
    @Operation(summary = "Get gym days by user ID")
    public List<GymDayDTO> getGymDaysByUser(@PathVariable Long userId) {
        return gymDayService.getGymDaysByUserId(userId);
    }

    @PostMapping("")
    @Operation(summary = "Save a gym day")
    public GymDayDTO saveGymDay(@Valid @RequestBody GymDayDTO gymDayDTO) {
        return gymDayService.addGymDay(gymDayDTO);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get a gym day by ID")
    public GymDayDTO getGymDay(@PathVariable Long id){
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

    @GetMapping("/user/{userId}/export")
    @Operation(summary = "Export workout history for a user")
    public ResponseEntity<String> exportWorkouts(
            @PathVariable Long userId,
            @RequestParam(defaultValue = "xml") String format) {
        
        List<GymDayDTO> workouts = gymDayService.getGymDaysByUserId(userId);
        WorkoutExportDTO exportData = new WorkoutExportDTO(workouts);

        FileExporter exporter = com.gymbuddy.backend.exporter.ExporterFactory.getExporter(format);
        String content = exporter.exportData(exportData);

        MediaType contentType = format.equalsIgnoreCase("xml") ? MediaType.APPLICATION_XML : MediaType.TEXT_PLAIN;
        String filename = "workout_history." + format.toLowerCase();

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + filename)
                .contentType(contentType)
                .body(content);
    }
}
