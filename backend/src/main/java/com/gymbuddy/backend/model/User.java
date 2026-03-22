package com.gymbuddy.backend.model;

import lombok.*;

import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class User {
    private Long id;
    private String username;
    private String email;
    private List<GymDay> gymDays;
    private Integer age;
    private Float height;
    private Float weight;
}
