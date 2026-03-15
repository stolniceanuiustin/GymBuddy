package com.gymbuddy.backend.repository;

import com.gymbuddy.backend.model.Exercise;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class ExerciseRepository {
    private final List<Exercise> exercises = new ArrayList<>();

    public Exercise save(Exercise exercise) {
        exercises.add(exercise);
        return exercise;
    }

    public List<Exercise> findAll() {
        return exercises;
    }

    public Exercise findById(Long id){
        return exercises.stream().
                filter(exercise -> exercise.getId().equals(id)).
                findFirst().orElse(null);
    }

    public Exercise updateExercise(Exercise exercise){
        Exercise oldExercise = findById(exercise.getId());
        if(oldExercise != null){
            oldExercise.setName(exercise.getName());
            oldExercise.setSets(exercise.getSets());
            // I don't update timeLogged here, to preserve the creation time. This is on porpouse
            return oldExercise;
        }
        return null;
    }

    public boolean deleteById(Long id){
        return exercises.removeIf(exercise -> exercise.getId().equals(id));
    }
}