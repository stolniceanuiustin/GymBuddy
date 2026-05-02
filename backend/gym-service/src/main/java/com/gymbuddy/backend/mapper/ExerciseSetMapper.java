package com.gymbuddy.backend.mapper;

import com.gymbuddy.backend.dto.ExerciseSetDTO;
import com.gymbuddy.backend.model.ExerciseSet;
import org.springframework.stereotype.Component;

@Component
public class ExerciseSetMapper {

    public ExerciseSetDTO toDTO(ExerciseSet entity) {
        if (entity == null) return null;
        return ExerciseSetDTO.builder()
                .id(entity.getId())
                .setNumber(entity.getSetNumber())
                .reps(entity.getReps())
                .weight(entity.getWeight())
                .build();
    }

    public ExerciseSet toEntity(ExerciseSetDTO dto) {
        if (dto == null) return null;
        return ExerciseSet.builder()
                .id(dto.getId())
                .setNumber(dto.getSetNumber())
                .reps(dto.getReps())
                .weight(dto.getWeight())
                .build();
    }
}
