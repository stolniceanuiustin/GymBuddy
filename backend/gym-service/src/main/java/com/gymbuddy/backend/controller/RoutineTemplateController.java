package com.gymbuddy.backend.controller;

import com.gymbuddy.backend.dto.RoutineTemplateDTO;
import com.gymbuddy.backend.service.RoutineTemplateService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/templates")
@CrossOrigin(origins = "http://localhost:5173")
@Tag(name = "Routine Templates", description = "Reusable workout routine management APIs")
public class RoutineTemplateController {

    private final RoutineTemplateService routineTemplateService;

    public RoutineTemplateController(RoutineTemplateService routineTemplateService) {
        this.routineTemplateService = routineTemplateService;
    }

    @PostMapping("")
    @Operation(summary = "Create a new routine template")
    public RoutineTemplateDTO createTemplate(@RequestBody RoutineTemplateDTO dto) {
        return routineTemplateService.createTemplate(dto);
    }

    @GetMapping("/user/{userId}")
    @Operation(summary = "Get templates for a specific user")
    public List<RoutineTemplateDTO> getTemplates(@PathVariable Long userId) {
        return routineTemplateService.getTemplatesByUser(userId);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a routine template")
    public void deleteTemplate(@PathVariable Long id) {
        routineTemplateService.deleteTemplate(id);
    }

    @PostMapping("/{id}/create-session")
    @Operation(summary = "Create a new gym session from a routine template")
    public com.gymbuddy.backend.dto.GymDayDTO createSessionFromTemplate(@PathVariable Long id) {
        return routineTemplateService.createGymDayFromTemplate(id);
    }
}
