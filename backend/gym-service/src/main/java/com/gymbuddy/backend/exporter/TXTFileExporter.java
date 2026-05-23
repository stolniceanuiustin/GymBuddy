package com.gymbuddy.backend.exporter;

import com.gymbuddy.backend.dto.GymDayDTO;
import com.gymbuddy.backend.dto.WorkoutExportDTO;

public class TXTFileExporter implements FileExporter {
    @Override
    public String exportData(Object object) {
        if (!(object instanceof WorkoutExportDTO)) {
            return object.toString();
        }

        WorkoutExportDTO exportData = (WorkoutExportDTO) object;
        StringBuilder sb = new StringBuilder();
        sb.append("=== WORKOUT HISTORY ===\n\n");

        for (GymDayDTO workout : exportData.getWorkouts()) {
            sb.append(String.format("Date: %s | Workout: %s\n", workout.getDate(), workout.getName()));
            sb.append("Notes: ").append(workout.getNotes()).append("\n");
            sb.append("---------------------------\n");
        }

        return sb.toString();
    }
}
