package com.gymbuddy.backend.model;

import lombok.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ExerciseSet {
    private int setNumber;
    private int reps;
    private double weight;
}
