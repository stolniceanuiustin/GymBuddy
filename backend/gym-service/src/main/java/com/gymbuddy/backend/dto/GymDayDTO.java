package com.gymbuddy.backend.dto;

import lombok.*;
import java.time.LocalDate;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class GymDayDTO {
    private Long id;
    private String name;
    private LocalDate date;
    private String notes;
    private int sleepQuality;
    private int energyLevel;
    private Long userId;
    private List<ExerciseDTO> exercises;
}
