package com.gymbuddy.backend.dto;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ExerciseSetDTO {
    private Long id;
    private int setNumber;
    private int reps;
    private Double weight;
}
