package com.gymbuddy.backend.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.LocalTime;
import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Schema(description = "Exercise entity")
public class Exercise {
    @Schema(description = "Unique identifier of the exercise", example = "1")
    private Long id;
    @Schema(description = "Type of the exercise")
    private ExerciseType exerciseType;
    @Schema(description = "Time when the exercise was logged", example = "14:30:00")
    private LocalTime timeLogged = LocalTime.now();
    @Schema(description = "List of sets performed for this exercise")
    private List<ExerciseSet> sets;
}
