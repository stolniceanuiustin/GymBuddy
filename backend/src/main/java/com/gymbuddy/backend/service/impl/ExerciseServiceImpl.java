package com.gymbuddy.backend.service.impl;

import com.gymbuddy.backend.model.Exercise;
import com.gymbuddy.backend.repository.ExerciseRepository;
import com.gymbuddy.backend.service.ExerciseService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class ExerciseServiceImpl implements ExerciseService {

    private final ExerciseRepository exerciseRepository;

    public ExerciseServiceImpl(ExerciseRepository exerciseRepository) {
        this.exerciseRepository = exerciseRepository;
    }

    @Override
    public Exercise addExercise(Exercise exercise) {
        return exerciseRepository.save(exercise);
    }

    @Override
    public List<Exercise> getAllExercises() {
        return exerciseRepository.findAll();
    }

    @Override
    public Exercise getExerciseById(Long id) {
        Exercise exercise = exerciseRepository.findById(id);
        if (exercise != null) {
            return exercise;
        }
        throw new NoSuchElementException("Exercise with id " + id + " not found");
    }

    @Override
    public Exercise updateExercise(Exercise exercise) {
        return exerciseRepository.updateExercise(exercise);
    }

    @Override
    public void deleteExercise(Long id) {
        exerciseRepository.deleteById(id);
    }
}