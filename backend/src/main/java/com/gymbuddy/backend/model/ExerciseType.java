package com.gymbuddy.backend.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Schema(description = "Exercise Type entity representing the category of an exercise")
public class ExerciseType {
    @Schema(description = "Unique identifier of the exercise type", example = "1")
    private Long id;
    @Schema(description = "Name of the exercise type", example = "Bench Press")
    private String name;
    @Schema(description = "Description of the exercise type", example = "A compound exercise for the chest")
    private String description;
}
