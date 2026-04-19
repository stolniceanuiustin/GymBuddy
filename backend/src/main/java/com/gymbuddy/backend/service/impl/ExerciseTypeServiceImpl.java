package com.gymbuddy.backend.service.impl;

import com.gymbuddy.backend.model.ExerciseType;
import com.gymbuddy.backend.repository.ExerciseTypeRepository;
import com.gymbuddy.backend.service.ExerciseTypeService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class ExerciseTypeServiceImpl implements ExerciseTypeService {

    private final ExerciseTypeRepository exerciseTypeRepository;

    public ExerciseTypeServiceImpl(ExerciseTypeRepository exerciseTypeRepository) {
        this.exerciseTypeRepository = exerciseTypeRepository;
    }

    @Override
    public ExerciseType addExerciseType(ExerciseType exerciseType) {
        return exerciseTypeRepository.save(exerciseType);
    }

    @Override
    public List<ExerciseType> getAllExerciseTypes() {
        return exerciseTypeRepository.findAll();
    }

    @Override
    public ExerciseType getExerciseTypeById(Long id) {
        ExerciseType exerciseType = exerciseTypeRepository.findById(id);
        if (exerciseType != null) {
            return exerciseType;
        }
        throw new NoSuchElementException("ExerciseType with id " + id + " not found");
    }

    @Override
    public ExerciseType updateExerciseType(ExerciseType exerciseType) {
        return exerciseTypeRepository.updateExerciseType(exerciseType);
    }

    @Override
    public void deleteExerciseType(Long id) {
        exerciseTypeRepository.deleteById(id);
    }
}
