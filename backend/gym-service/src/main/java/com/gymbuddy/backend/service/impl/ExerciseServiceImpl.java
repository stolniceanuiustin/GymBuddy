package com.gymbuddy.backend.service.impl;

import com.gymbuddy.backend.dto.ExerciseDTO;
import com.gymbuddy.backend.exception.ResourceNotFoundException;
import com.gymbuddy.backend.mapper.ExerciseMapper;
import com.gymbuddy.backend.model.Exercise;
import com.gymbuddy.backend.model.ExerciseSet;
import com.gymbuddy.backend.repository.ExerciseRepository;
import com.gymbuddy.backend.repository.ExerciseSetRepository;
import com.gymbuddy.backend.repository.GymDayRepository;
import com.gymbuddy.backend.service.AchievementService;
import com.gymbuddy.backend.service.ExerciseService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ExerciseServiceImpl implements ExerciseService {

    private final ExerciseRepository exerciseRepository;
    private final ExerciseSetRepository exerciseSetRepository;
    private final GymDayRepository gymDayRepository;
    private final AchievementService achievementService;
    private final ExerciseMapper exerciseMapper;

    public ExerciseServiceImpl(ExerciseRepository exerciseRepository, 
                               ExerciseSetRepository exerciseSetRepository,
                               GymDayRepository gymDayRepository,
                               AchievementService achievementService,
                               ExerciseMapper exerciseMapper) {
        this.exerciseRepository = exerciseRepository;
        this.exerciseSetRepository = exerciseSetRepository;
        this.gymDayRepository = gymDayRepository;
        this.achievementService = achievementService;
        this.exerciseMapper = exerciseMapper;
    }

    @Override
    public ExerciseDTO addExercise(ExerciseDTO exerciseDTO) {
        Exercise exercise = exerciseMapper.toEntity(exerciseDTO);
        return exerciseMapper.toDTO(exerciseRepository.save(exercise));
    }

    @Override
    public List<ExerciseDTO> getAllExercises() {
        return exerciseRepository.findAll().stream()
                .map(exerciseMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public ExerciseDTO getExerciseById(Long id) {
        Exercise exercise = exerciseRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Exercise", "id", id));
        return exerciseMapper.toDTO(exercise);
    }

    @Override
    public ExerciseDTO updateExercise(ExerciseDTO exerciseDTO) {
        if (!exerciseRepository.existsById(exerciseDTO.getId())) {
            throw new ResourceNotFoundException("Exercise", "id", exerciseDTO.getId());
        }
        Exercise exercise = exerciseMapper.toEntity(exerciseDTO);
        return exerciseMapper.toDTO(exerciseRepository.save(exercise));
    }

    @Override
    public void deleteExercise(Long id) {
        if (!exerciseRepository.existsById(id)) {
            throw new ResourceNotFoundException("Exercise", "id", id);
        }
        exerciseRepository.deleteById(id);
    }

    @Override
    public void addSetToExercise(Long exerciseId, Long setId) {
        Exercise exercise = exerciseRepository.findById(exerciseId)
                .orElseThrow(() -> new ResourceNotFoundException("Exercise", "id", exerciseId));
        ExerciseSet set = exerciseSetRepository.findById(setId)
                .orElseThrow(() -> new ResourceNotFoundException("ExerciseSet", "id", setId));
        
        if (exercise.getSets() == null) {
            exercise.setSets(new ArrayList<>());
        }
        exercise.getSets().add(set);
        exerciseRepository.save(exercise);

        gymDayRepository.findByExerciseId(exerciseId).ifPresent(gymDay -> {
            if (gymDay.getUser() != null && exercise.getExerciseType() != null) {
                achievementService.checkPersonalBest(
                    gymDay.getUser().getId(),
                    exercise.getExerciseType().getId(),
                    exercise.getExerciseType().getName(),
                    set
                );
            }
        });
    }
}
