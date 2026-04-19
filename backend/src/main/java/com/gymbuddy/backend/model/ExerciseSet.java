package com.gymbuddy.backend.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.LocalTime;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Schema(description = "Exercise set entity representing one set of an exercise")
public class ExerciseSet {
    @Schema(description = "Unique identifier of the set", example = "1")
    private Long id;
    @Schema(description = "Set number in the exercise session", example = "1")
    private int setNumber;
    @Schema(description = "Number of repetitions performed", example = "12")
    private int reps;
    @Schema(description = "Weight used for this set in kg", example = "60.0")
    private Double weight;
    @Builder.Default
    @Schema(description = "Time when the set was logged", example = "14:35:00")
    private LocalTime timeLogged = LocalTime.now();
}
