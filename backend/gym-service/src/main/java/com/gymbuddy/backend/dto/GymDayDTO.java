package com.gymbuddy.backend.dto;

import jakarta.validation.constraints.*;
import lombok.*;
import java.time.LocalDate;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class GymDayDTO {
    private Long id;

    @NotBlank(message = "Workout name is required")
    private String name;

    @NotNull(message = "Workout date is required")
    private LocalDate date;

    private String notes;

    @Min(value = 1, message = "Sleep quality must be at least 1")
    @Max(value = 10, message = "Sleep quality must be at most 10")
    private int sleepQuality;

    @Min(value = 1, message = "Energy level must be at least 1")
    @Max(value = 10, message = "Energy level must be at most 10")
    private int energyLevel;

    @NotNull(message = "User ID is required")
    private Long userId;

    @NotEmpty(message = "A workout must contain at least one exercise")
    private List<ExerciseDTO> exercises;
}
