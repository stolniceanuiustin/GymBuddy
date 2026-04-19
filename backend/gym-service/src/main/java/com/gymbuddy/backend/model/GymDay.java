package com.gymbuddy.backend.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "gym_days")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class GymDay {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String name;
    private LocalDate date;
    private String notes;
    private int sleepQuality;
    private int energyLevel;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "gym_day_id")
    private List<Exercise> exercises;
}
