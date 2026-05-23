package com.gymbuddy.backend.dto;

import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@XmlRootElement(name = "WorkoutHistory")
public class WorkoutExportDTO {
    
    private List<GymDayDTO> workouts;

    @XmlElement(name = "GymDay")
    public List<GymDayDTO> getWorkouts() {
        return workouts;
    }
}
