package com.gymbuddy.backend.dto;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import lombok.*;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@XmlRootElement(name = "Exercise")
@XmlAccessorType(XmlAccessType.FIELD)
public class ExerciseDTO {
    private Long id;
    private ExerciseTypeDTO exerciseType;
    
    @XmlElement(name = "Set")
    private List<ExerciseSetDTO> sets;
}
