package com.gymbuddy.backend.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ExerciseSetDTO {
    private Long id;

    @Min(value = 1, message = "Set number must be at least 1")
    private int setNumber;

    @Min(value = 0, message = "Reps cannot be negative")
    private int reps;

    @NotNull(message = "Weight is required")
    @Min(value = 0, message = "Weight cannot be negative")
    private Double weight;
}
