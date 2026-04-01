package com.gymbuddy.backend.model;

import lombok.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ExerciseType {
    private Long id;
    private String name;
    private String description;
}
