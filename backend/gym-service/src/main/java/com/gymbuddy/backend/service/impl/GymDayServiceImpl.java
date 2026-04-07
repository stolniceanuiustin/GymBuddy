package com.gymbuddy.backend.service.impl;

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
import java.util.NoSuchElementException;

@Service
public class GymDayServiceImpl implements GymDayService {

    private final GymDayRepository gymDayRepository;
    private final UserRepository userRepository;
    private final ExerciseRepository exerciseRepository;

    public GymDayServiceImpl(GymDayRepository gymDayRepository, UserRepository userRepository, ExerciseRepository exerciseRepository) {
        this.gymDayRepository = gymDayRepository;
        this.userRepository = userRepository;
        this.exerciseRepository = exerciseRepository;
    }

    @Override
    public GymDay addGymDay(GymDay gymDay) {
        if (gymDay.getUser() == null) {
            List<User> users = userRepository.findAll();
            if (!users.isEmpty()) {
                gymDay.setUser(users.get(0));
            }
        }
        if (gymDay.getId() == null) {
            gymDay.setId((long) (gymDayRepository.findAll().size() + 1));
            return gymDayRepository.save(gymDay);
        } else {
            return gymDayRepository.updateGymDay(gymDay);
        }
    }

    @Override
    public List<GymDay> getAllGymDays() {
        return gymDayRepository.findAll();
    }

    @Override
    public GymDay getGymDayById(Long id) {
        GymDay gymDay = gymDayRepository.findById(id);
        if (gymDay != null) {
            return gymDay;
        }
        throw new NoSuchElementException("GymDay with id " + id + " not found");
    }

    @Override
    public List<GymDay> getGymDaysByUserId(Long userId) {
        return gymDayRepository.findByUserId(userId);
    }

    @Override
    public GymDay updateGymDay(GymDay gymDay) {
        return gymDayRepository.updateGymDay(gymDay);
    }

    @Override
    public void deleteGymDay(Long id) {
        gymDayRepository.deleteById(id);
    }

    @Override
    public void addExerciseToGymDay(Long gymDayId, Long exerciseId) {
        GymDay gymDay = getGymDayById(gymDayId);
        Exercise exercise = exerciseRepository.findById(exerciseId);
        if (exercise != null) {
            if (gymDay.getExercises() == null) {
                gymDay.setExercises(new ArrayList<>());
            }
            gymDay.getExercises().add(exercise);
        }
    }
    }