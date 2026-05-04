package com.gymbuddy.backend.service.impl;

import com.gymbuddy.backend.dto.GymDayDTO;
import com.gymbuddy.backend.exception.ResourceNotFoundException;
import com.gymbuddy.backend.mapper.GymDayMapper;
import com.gymbuddy.backend.model.Exercise;
import com.gymbuddy.backend.model.ExerciseSet;
import com.gymbuddy.backend.model.GymDay;
import com.gymbuddy.backend.model.User;
import com.gymbuddy.backend.repository.ExerciseRepository;
import com.gymbuddy.backend.repository.GymDayRepository;
import com.gymbuddy.backend.repository.UserRepository;
import com.gymbuddy.backend.service.AchievementService;
import com.gymbuddy.backend.service.GymDayService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class GymDayServiceImpl implements GymDayService {

    private final GymDayRepository gymDayRepository;
    private final UserRepository userRepository;
    private final ExerciseRepository exerciseRepository;
    private final AchievementService achievementService;
    private final GymDayMapper gymDayMapper;

    public GymDayServiceImpl(GymDayRepository gymDayRepository, UserRepository userRepository, 
                             ExerciseRepository exerciseRepository, AchievementService achievementService,
                             GymDayMapper gymDayMapper) {
        this.gymDayRepository = gymDayRepository;
        this.userRepository = userRepository;
        this.exerciseRepository = exerciseRepository;
        this.achievementService = achievementService;
        this.gymDayMapper = gymDayMapper;
    }

    @Override
    @Transactional
    public GymDayDTO addGymDay(GymDayDTO gymDayDTO) {
        GymDay gymDay = gymDayMapper.toEntity(gymDayDTO);
        if (gymDay.getUser() == null) {
            List<User> users = userRepository.findAll();
            if (!users.isEmpty()) {
                gymDay.setUser(users.get(0));
            }
        }
        GymDay savedGymDay = gymDayRepository.save(gymDay);
        checkAchievements(savedGymDay);
        return gymDayMapper.toDTO(savedGymDay);
    }

    private void checkAchievements(GymDay gymDay) {
        if (gymDay.getUser() != null && gymDay.getExercises() != null) {
            for (Exercise exercise : gymDay.getExercises()) {
                if (exercise.getExerciseType() != null && exercise.getSets() != null) {
                    for (ExerciseSet set : exercise.getSets()) {
                        achievementService.checkPersonalBest(
                            gymDay.getUser().getId(),
                            exercise.getExerciseType().getId(),
                            exercise.getExerciseType().getName(),
                            set
                        );
                    }
                }
            }
        }
    }

    @Override
    public List<GymDayDTO> getAllGymDays() {
        return gymDayRepository.findAll().stream()
                .map(gymDayMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public GymDayDTO getGymDayById(Long id) {
        GymDay gymDay = gymDayRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("GymDay", "id", id));
        return gymDayMapper.toDTO(gymDay);
    }

    @Override
    public List<GymDayDTO> getGymDaysByUserId(Long userId) {
        return gymDayRepository.findByUserId(userId).stream()
                .map(gymDayMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public GymDayDTO updateGymDay(GymDayDTO gymDayDTO) {
        if (!gymDayRepository.existsById(gymDayDTO.getId())) {
            throw new ResourceNotFoundException("GymDay", "id", gymDayDTO.getId());
        }
        GymDay gymDay = gymDayMapper.toEntity(gymDayDTO);
        GymDay savedGymDay = gymDayRepository.save(gymDay);
        checkAchievements(savedGymDay);
        return gymDayMapper.toDTO(savedGymDay);
    }

    @Override
    public void deleteGymDay(Long id) {
        if (!gymDayRepository.existsById(id)) {
            throw new ResourceNotFoundException("GymDay", "id", id);
        }
        gymDayRepository.deleteById(id);
    }

    @Override
    public void addExerciseToGymDay(Long gymDayId, Long exerciseId) {
        GymDay gymDay = gymDayRepository.findById(gymDayId)
                .orElseThrow(() -> new ResourceNotFoundException("GymDay", "id", gymDayId));
        Exercise exercise = exerciseRepository.findById(exerciseId)
                .orElseThrow(() -> new ResourceNotFoundException("Exercise", "id", exerciseId));
        
        if (gymDay.getExercises() == null) {
            gymDay.setExercises(new ArrayList<>());
        }
        gymDay.getExercises().add(exercise);
        gymDayRepository.save(gymDay);
    }
}
