package com.gymbuddy.backend.mapper;

import com.gymbuddy.backend.dto.ExerciseTypeDTO;
import com.gymbuddy.backend.model.ExerciseType;
import org.springframework.stereotype.Component;

@Component
public class ExerciseTypeMapper {

    public ExerciseTypeDTO toDTO(ExerciseType entity) {
        if (entity == null) return null;
        return ExerciseTypeDTO.builder()
                .id(entity.getId())
                .name(entity.getName())
                .description(entity.getDescription())
                .bodyweight(entity.isBodyweight())
                .build();
    }

    public ExerciseType toEntity(ExerciseTypeDTO dto) {
        if (dto == null) return null;
        return ExerciseType.builder()
                .id(dto.getId())
                .name(dto.getName())
                .description(dto.getDescription())
                .bodyweight(dto.isBodyweight())
                .build();
    }
}
