package com.gymbuddy.backend.dto;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ExerciseTypeDTO {
    private Long id;
    private String name;
    private String description;
    private boolean bodyweight;
}
