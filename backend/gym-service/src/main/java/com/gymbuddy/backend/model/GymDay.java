package com.gymbuddy.backend.model;


import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDate;
import java.util.List;
import lombok.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Schema(description = "Gym Day entity representing a single workout session")
public class GymDay {
    @Schema(description = "Unique identifier of the gym day", example = "1")
    private Long id;
    @Schema(description = "Date of the workout", example = "2024-03-20")
    private LocalDate date;
    @Schema(description = "Name of the workout session", example = "Chest & Triceps")
    private String name;
    @Schema(description = "User who performed the workout")
    private User user;
    @Schema(description = "List of exercises performed during this session")
    private List<Exercise> exercises;
    @Schema(description = "Additional notes for the session", example = "Felt strong today")
    private String notes;
    @Schema(description = "Quality of sleep before the workout (1-10)", example = "8")
    private Integer sleepQuality;
    @Schema(description = "Energy level during the workout (1-10)", example = "9")
    private Integer energyLevel;
}
