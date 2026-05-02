package com.gymbuddy.backend.dto;

import lombok.*;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ExerciseDTO {
    private Long id;
    private ExerciseTypeDTO exerciseType;
    private List<ExerciseSetDTO> sets;
}
