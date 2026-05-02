package com.gymbuddy.backend.service.impl;

import com.gymbuddy.backend.dto.GymDayDTO;
import com.gymbuddy.backend.exception.ResourceNotFoundException;
import com.gymbuddy.backend.mapper.GymDayMapper;
import com.gymbuddy.backend.model.Exercise;
import com.gymbuddy.backend.model.GymDay;
import com.gymbuddy.backend.model.User;
import com.gymbuddy.backend.repository.ExerciseRepository;
import com.gymbuddy.backend.repository.GymDayRepository;
import com.gymbuddy.backend.repository.UserRepository;
import com.gymbuddy.backend.service.GymDayService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class GymDayServiceImpl implements GymDayService {

    private final GymDayRepository gymDayRepository;
    private final UserRepository userRepository;
    private final ExerciseRepository exerciseRepository;
    private final GymDayMapper gymDayMapper;

    public GymDayServiceImpl(GymDayRepository gymDayRepository, UserRepository userRepository, 
                             ExerciseRepository exerciseRepository, GymDayMapper gymDayMapper) {
        this.gymDayRepository = gymDayRepository;
        this.userRepository = userRepository;
        this.exerciseRepository = exerciseRepository;
        this.gymDayMapper = gymDayMapper;
    }

    @Override
    public GymDayDTO addGymDay(GymDayDTO gymDayDTO) {
        GymDay gymDay = gymDayMapper.toEntity(gymDayDTO);
        if (gymDay.getUser() == null) {
            List<User> users = userRepository.findAll();
            if (!users.isEmpty()) {
                gymDay.setUser(users.get(0));
            }
        }
        return gymDayMapper.toDTO(gymDayRepository.save(gymDay));
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
    public GymDayDTO updateGymDay(GymDayDTO gymDayDTO) {
        if (!gymDayRepository.existsById(gymDayDTO.getId())) {
            throw new ResourceNotFoundException("GymDay", "id", gymDayDTO.getId());
        }
        GymDay gymDay = gymDayMapper.toEntity(gymDayDTO);
        return gymDayMapper.toDTO(gymDayRepository.save(gymDay));
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
