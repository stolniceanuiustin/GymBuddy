package com.gymbuddy.backend.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "exercise_types")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ExerciseType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(unique = true, nullable = false)
    private String name;
    
    private String description;

    @Column(name = "is_bodyweight")
    private boolean bodyweight;
}
