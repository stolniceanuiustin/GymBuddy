package com.gymbuddy.backend.model;

import lombok.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter

public class Exercise {
    private Long id;
    private String name;
    private int sets;
    private int reps;
    private double weight;
}
