package com.gymbuddy.backend.dto;

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
@XmlRootElement(name = "ExerciseType")
@XmlAccessorType(XmlAccessType.FIELD)
public class ExerciseTypeDTO {
    private Long id;
    private String name;
    private String description;
    private boolean bodyweight;
}
