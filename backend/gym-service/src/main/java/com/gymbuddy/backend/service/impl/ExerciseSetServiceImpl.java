package com.gymbuddy.backend.service.impl;

import com.gymbuddy.backend.dto.ExerciseSetDTO;
import com.gymbuddy.backend.exception.ResourceNotFoundException;
import com.gymbuddy.backend.exception.ValidationException;
import com.gymbuddy.backend.mapper.ExerciseSetMapper;
import com.gymbuddy.backend.model.ExerciseSet;
import com.gymbuddy.backend.repository.ExerciseSetRepository;
import com.gymbuddy.backend.service.ExerciseSetService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ExerciseSetServiceImpl implements ExerciseSetService {

    private final ExerciseSetRepository exerciseSetRepository;
    private final ExerciseSetMapper exerciseSetMapper;

    public ExerciseSetServiceImpl(ExerciseSetRepository exerciseSetRepository, ExerciseSetMapper exerciseSetMapper) {
        this.exerciseSetRepository = exerciseSetRepository;
        this.exerciseSetMapper = exerciseSetMapper;
    }

    @Override
    public ExerciseSetDTO addExerciseSet(ExerciseSetDTO exerciseSetDTO) {
        validateExerciseSet(exerciseSetDTO);
        ExerciseSet exerciseSet = exerciseSetMapper.toEntity(exerciseSetDTO);
        return exerciseSetMapper.toDTO(exerciseSetRepository.save(exerciseSet));
    }

    @Override
    public List<ExerciseSetDTO> getAllExerciseSets() {
        return exerciseSetRepository.findAll().stream()
                .map(exerciseSetMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public ExerciseSetDTO getExerciseSetById(Long id) {
        ExerciseSet exerciseSet = exerciseSetRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("ExerciseSet", "id", id));
        return exerciseSetMapper.toDTO(exerciseSet);
    }

    @Override
    public ExerciseSetDTO updateExerciseSet(ExerciseSetDTO exerciseSetDTO) {
        if (!exerciseSetRepository.existsById(exerciseSetDTO.getId())) {
            throw new ResourceNotFoundException("ExerciseSet", "id", exerciseSetDTO.getId());
        }
        validateExerciseSet(exerciseSetDTO);
        ExerciseSet exerciseSet = exerciseSetMapper.toEntity(exerciseSetDTO);
        return exerciseSetMapper.toDTO(exerciseSetRepository.save(exerciseSet));
    }

    @Override
    public void deleteExerciseSet(Long id) {
        if (!exerciseSetRepository.existsById(id)) {
            throw new ResourceNotFoundException("ExerciseSet", "id", id);
        }
        exerciseSetRepository.deleteById(id);
    }

    private void validateExerciseSet(ExerciseSetDTO dto) {
        if (dto.getReps() < 0) {
            throw new ValidationException("Reps cannot be negative");
        }
        if (dto.getWeight() != null && (dto.getWeight() < 0 || dto.getWeight() > 500)) {
            throw new ValidationException("Weight must be between 0 and 500 kg");
        }
    }
}
