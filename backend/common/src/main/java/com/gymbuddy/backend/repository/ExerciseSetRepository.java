package com.gymbuddy.backend.repository;

import com.gymbuddy.backend.model.ExerciseSet;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class ExerciseSetRepository {
    private final List<ExerciseSet> exerciseSets = new ArrayList<>();

    public ExerciseSet save(ExerciseSet exerciseSet) {
        exerciseSets.add(exerciseSet);
        return exerciseSet;
    }

    public List<ExerciseSet> findAll() {
        return exerciseSets;
    }

    public ExerciseSet findById(Long id){
        return exerciseSets.stream().
                filter(exerciseSet -> exerciseSet.getId().equals(id)).
                findFirst().orElse(null);
    }

    public ExerciseSet updateExerciseSet(ExerciseSet exerciseSet){
        ExerciseSet oldExerciseSet = findById(exerciseSet.getId());
        if(oldExerciseSet != null){
            oldExerciseSet.setSetNumber(exerciseSet.getSetNumber());
            oldExerciseSet.setReps(exerciseSet.getReps());
            oldExerciseSet.setWeight(exerciseSet.getWeight());
            return oldExerciseSet;
        }
        return null;
    }

    public boolean deleteById(Long id){
        return exerciseSets.removeIf(exerciseSet -> exerciseSet.getId().equals(id));
    }
}