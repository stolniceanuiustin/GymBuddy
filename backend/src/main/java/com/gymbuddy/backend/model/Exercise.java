package com.gymbuddy.backend.model;

import lombok.*;

import java.time.LocalTime;
import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Exercise {
    private Long id;
    private ExerciseType exerciseType;
    private LocalTime timeLogged = LocalTime.now();
    private List<ExerciseSet> sets;
}
