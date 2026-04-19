package com.gymbuddy.backend.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "users")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Schema(description = "User entity")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Unique identifier of the user", example = "1")
    private Long id;

    @Column(unique = true, nullable = false)
    @Schema(description = "Username of the user", example = "john_doe")
    private String username;

    @Column(unique = true, nullable = false)
    @Schema(description = "Email address of the user", example = "john.doe@example.com")
    private String email;

    @Column(nullable = false)
    @Schema(description = "Password of the user", example = "secret123")
    private String password;

    @Enumerated(EnumType.STRING)
    @Schema(description = "Role of the user")
    private Role role;

    @JsonIgnore
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    @Schema(description = "List of gym days associated with the user", accessMode = Schema.AccessMode.READ_ONLY)
    private List<GymDay> gymDays;

    @Schema(description = "Age of the user", example = "25")
    private Integer age;

    @Schema(description = "Height of the user in cm", example = "180.5")
    private Float height;

    @Schema(description = "Weight of the user in kg", example = "75.0")
    private Float weight;
}
