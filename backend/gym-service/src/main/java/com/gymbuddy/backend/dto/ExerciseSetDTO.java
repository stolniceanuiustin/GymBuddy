package com.gymbuddy.backend.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlRootElement;
import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@XmlRootElement(name = "Set")
@XmlAccessorType(XmlAccessType.FIELD)
public class ExerciseSetDTO {
    private Long id;

    @Min(value = 1, message = "Set number must be at least 1")
    private int setNumber;

    @Min(value = 0, message = "Reps cannot be negative")
    private int reps;

    @NotNull(message = "Weight is required")
    @Min(value = 0, message = "Weight cannot be negative")
    private Double weight;
}
