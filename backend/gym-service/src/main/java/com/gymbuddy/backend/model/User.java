package com.gymbuddy.backend.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Schema(description = "User entity")
public class User {
    @Schema(description = "Unique identifier of the user", example = "1")
    private Long id;
    @Schema(description = "Username of the user", example = "john_doe")
    private String username;
    @Schema(description = "Email address of the user", example = "john.doe@example.com")
    private String email;
    @Schema(description = "Password of the user", example = "secret123")
    private String password;
    @Schema(description = "Role of the user")
    private Role role;
    @JsonIgnore
    @Schema(description = "List of gym days associated with the user", accessMode = Schema.AccessMode.READ_ONLY)
    private List<GymDay> gymDays;
    @Schema(description = "Age of the user", example = "25")
    private Integer age;
    @Schema(description = "Height of the user in cm", example = "180.5")
    private Float height;
    @Schema(description = "Weight of the user in kg", example = "75.0")
    private Float weight;
}
