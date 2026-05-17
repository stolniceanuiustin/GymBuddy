package com.gymbuddy.backend.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "routine_templates")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class RoutineTemplate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private Long userId;

    @ManyToMany
    @JoinTable(
        name = "template_exercises",
        joinColumns = @JoinColumn(name = "template_id"),
        inverseJoinColumns = @JoinColumn(name = "exercise_type_id")
    )
    private List<ExerciseType> exerciseTypes;
}
