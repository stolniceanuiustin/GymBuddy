package com.gymbuddy.backend.service.impl;

import com.gymbuddy.backend.model.ExerciseSet;
import com.gymbuddy.backend.repository.ExerciseSetRepository;
import com.gymbuddy.backend.service.ExerciseSetService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class ExerciseSetServiceImpl implements ExerciseSetService {

    private final ExerciseSetRepository exerciseSetRepository;

    public ExerciseSetServiceImpl(ExerciseSetRepository exerciseSetRepository) {
        this.exerciseSetRepository = exerciseSetRepository;
    }

    @Override
    public ExerciseSet addExerciseSet(ExerciseSet exerciseSet) {
        return exerciseSetRepository.save(exerciseSet);
    }

    @Override
    public List<ExerciseSet> getAllExerciseSets() {
        return exerciseSetRepository.findAll();
    }

    @Override
    public ExerciseSet getExerciseSetById(Long id) {
        ExerciseSet exerciseSet = exerciseSetRepository.findById(id);
        if (exerciseSet != null) {
            return exerciseSet;
        }
        throw new NoSuchElementException("ExerciseSet with id " + id + " not found");
    }

    @Override
    public ExerciseSet updateExerciseSet(ExerciseSet exerciseSet) {
        return exerciseSetRepository.updateExerciseSet(exerciseSet);
    }

    @Override
    public void deleteExerciseSet(Long id) {
        exerciseSetRepository.deleteById(id);
    }
}