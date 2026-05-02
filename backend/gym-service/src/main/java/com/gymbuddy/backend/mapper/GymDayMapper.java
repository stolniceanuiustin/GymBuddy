package com.gymbuddy.backend.mapper;

import com.gymbuddy.backend.dto.GymDayDTO;
import com.gymbuddy.backend.model.GymDay;
import com.gymbuddy.backend.model.User;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.stream.Collectors;

@Component
public class GymDayMapper {

    private final ExerciseMapper exerciseMapper;

    public GymDayMapper(ExerciseMapper exerciseMapper) {
        this.exerciseMapper = exerciseMapper;
    }

    public GymDayDTO toDTO(GymDay entity) {
        if (entity == null) return null;
        return GymDayDTO.builder()
                .id(entity.getId())
                .name(entity.getName())
                .date(entity.getDate())
                .notes(entity.getNotes())
                .sleepQuality(entity.getSleepQuality())
                .energyLevel(entity.getEnergyLevel())
                .userId(entity.getUser() != null ? entity.getUser().getId() : null)
                .exercises(entity.getExercises() != null ? 
                        entity.getExercises().stream().map(exerciseMapper::toDTO).collect(Collectors.toList()) : 
                        Collections.emptyList())
                .build();
    }

    public GymDay toEntity(GymDayDTO dto) {
        if (dto == null) return null;
        GymDay entity = new GymDay();
        entity.setId(dto.getId());
        entity.setName(dto.getName());
        entity.setDate(dto.getDate());
        entity.setNotes(dto.getNotes());
        entity.setSleepQuality(dto.getSleepQuality());
        entity.setEnergyLevel(dto.getEnergyLevel());
        if (dto.getUserId() != null) {
            User user = new User();
            user.setId(dto.getUserId());
            entity.setUser(user);
        }
        entity.setExercises(dto.getExercises() != null ? 
                dto.getExercises().stream().map(exerciseMapper::toEntity).collect(Collectors.toList()) : 
                Collections.emptyList());
        return entity;
    }
}
