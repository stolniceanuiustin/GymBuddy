package com.gymbuddy.backend.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
    private Role role;
    @JsonIgnore
    private List<GymDay> gymDays;
    private Integer age;
    private Float height;
    private Float weight;
}
