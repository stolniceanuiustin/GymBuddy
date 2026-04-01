package com.gymbuddy.backend.model;

import lombok.*;

import java.time.LocalTime;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ExerciseSet {
    private Long id;
    private int setNumber;
    private int reps;
    private Double weight;
    @Builder.Default
    private LocalTime timeLogged = LocalTime.now();
}
