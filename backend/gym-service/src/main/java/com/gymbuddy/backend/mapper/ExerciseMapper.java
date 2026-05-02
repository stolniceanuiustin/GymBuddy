package com.gymbuddy.backend.mapper;

import com.gymbuddy.backend.dto.ExerciseDTO;
import com.gymbuddy.backend.model.Exercise;
import com.gymbuddy.backend.model.ExerciseType;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.stream.Collectors;

@Component
public class ExerciseMapper {

    private final ExerciseSetMapper exerciseSetMapper;
    private final ExerciseTypeMapper exerciseTypeMapper;

    public ExerciseMapper(ExerciseSetMapper exerciseSetMapper, ExerciseTypeMapper exerciseTypeMapper) {
        this.exerciseSetMapper = exerciseSetMapper;
        this.exerciseTypeMapper = exerciseTypeMapper;
    }

    public ExerciseDTO toDTO(Exercise entity) {
        if (entity == null) return null;
        return ExerciseDTO.builder()
                .id(entity.getId())
                .exerciseType(exerciseTypeMapper.toDTO(entity.getExerciseType()))
                .sets(entity.getSets() != null ? 
                        entity.getSets().stream().map(exerciseSetMapper::toDTO).collect(Collectors.toList()) : 
                        Collections.emptyList())
                .build();
    }

    public Exercise toEntity(ExerciseDTO dto) {
        if (dto == null) return null;
        Exercise entity = new Exercise();
        entity.setId(dto.getId());
        entity.setExerciseType(exerciseTypeMapper.toEntity(dto.getExerciseType()));
        entity.setSets(dto.getSets() != null ? 
                dto.getSets().stream().map(exerciseSetMapper::toEntity).collect(Collectors.toList()) : 
                Collections.emptyList());
        return entity;
    }
}
