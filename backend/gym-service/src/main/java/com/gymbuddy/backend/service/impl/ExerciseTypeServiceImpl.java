package com.gymbuddy.backend.service.impl;

import com.gymbuddy.backend.dto.ExerciseTypeDTO;
import com.gymbuddy.backend.exception.ResourceNotFoundException;
import com.gymbuddy.backend.mapper.ExerciseTypeMapper;
import com.gymbuddy.backend.model.ExerciseType;
import com.gymbuddy.backend.repository.ExerciseTypeRepository;
import com.gymbuddy.backend.service.ExerciseTypeService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ExerciseTypeServiceImpl implements ExerciseTypeService {

    private final ExerciseTypeRepository exerciseTypeRepository;
    private final ExerciseTypeMapper exerciseTypeMapper;

    public ExerciseTypeServiceImpl(ExerciseTypeRepository exerciseTypeRepository, ExerciseTypeMapper exerciseTypeMapper) {
        this.exerciseTypeRepository = exerciseTypeRepository;
        this.exerciseTypeMapper = exerciseTypeMapper;
    }

    @Override
    public ExerciseTypeDTO addExerciseType(ExerciseTypeDTO exerciseTypeDTO) {
        ExerciseType exerciseType = exerciseTypeMapper.toEntity(exerciseTypeDTO);
        return exerciseTypeMapper.toDTO(exerciseTypeRepository.save(exerciseType));
    }

    @Override
    public List<ExerciseTypeDTO> getAllExerciseTypes() {
        return exerciseTypeRepository.findAll().stream()
                .map(exerciseTypeMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public ExerciseTypeDTO getExerciseTypeById(Long id) {
        ExerciseType exerciseType = exerciseTypeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("ExerciseType", "id", id));
        return exerciseTypeMapper.toDTO(exerciseType);
    }

    @Override
    public ExerciseTypeDTO updateExerciseType(ExerciseTypeDTO exerciseTypeDTO) {
        if (!exerciseTypeRepository.existsById(exerciseTypeDTO.getId())) {
            throw new ResourceNotFoundException("ExerciseType", "id", exerciseTypeDTO.getId());
        }
        ExerciseType exerciseType = exerciseTypeMapper.toEntity(exerciseTypeDTO);
        return exerciseTypeMapper.toDTO(exerciseTypeRepository.save(exerciseType));
    }

    @Override
    public void deleteExerciseType(Long id) {
        if (!exerciseTypeRepository.existsById(id)) {
            throw new ResourceNotFoundException("ExerciseType", "id", id);
        }
        exerciseTypeRepository.deleteById(id);
    }
}
