package com.gymbuddy.backend.model;


import java.time.LocalDate;
import java.util.List;
import lombok.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter

public class GymDay {
    private Long id;
    private LocalDate date;
    private String name;
    private User user;
    private List<Exercise> exercises;
    private String notes;
    private Integer sleepQuality;
    private Integer energyLevel;
}
