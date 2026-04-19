package com.gymbuddy.backend.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "exercise_sets")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ExerciseSet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private int setNumber;
    private int reps;
    private Double weight;
}
