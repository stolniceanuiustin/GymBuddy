package com.gymbuddy.backend.repository;

import com.gymbuddy.backend.model.ExerciseType;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class ExerciseTypeRepository {
    private final List<ExerciseType> exerciseTypes = new ArrayList<>();

    public ExerciseType save(ExerciseType exerciseType) {
        if (exerciseType.getId() == null) {
            exerciseType.setId((long) (exerciseTypes.size() + 1));
        }
        exerciseTypes.add(exerciseType);
        return exerciseType;
    }

    public List<ExerciseType> findAll() {
        return exerciseTypes;
    }

    public ExerciseType findById(Long id) {
        return exerciseTypes.stream()
                .filter(type -> type.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    public ExerciseType updateExerciseType(ExerciseType exerciseType) {
        ExerciseType oldType = findById(exerciseType.getId());
        if (oldType != null) {
            oldType.setName(exerciseType.getName());
            oldType.setDescription(exerciseType.getDescription());
            return oldType;
        }
        return null;
    }

    public boolean deleteById(Long id) {
        return exerciseTypes.removeIf(type -> type.getId().equals(id));
    }
}
