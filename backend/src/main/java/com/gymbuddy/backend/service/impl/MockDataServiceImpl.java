package com.gymbuddy.backend.service.impl;

import com.gymbuddy.backend.model.*;
import com.gymbuddy.backend.repository.*;
import com.gymbuddy.backend.service.MockDataService;
import jakarta.annotation.PostConstruct;
import net.datafaker.Faker;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service
public class MockDataServiceImpl implements MockDataService {
    private final Faker faker = new Faker();
    private final Random random = new Random();

    private final UserRepository userRepository;
    private final GymDayRepository gymDayRepository;
    private final ExerciseRepository exerciseRepository;
    private final ExerciseSetRepository exerciseSetRepository;

    public MockDataServiceImpl(UserRepository userRepository,
                               GymDayRepository gymDayRepository,
                               ExerciseRepository exerciseRepository,
                               ExerciseSetRepository exerciseSetRepository) {
        this.userRepository = userRepository;
        this.gymDayRepository = gymDayRepository;
        this.exerciseRepository = exerciseRepository;
        this.exerciseSetRepository = exerciseSetRepository;
    }

    @PostConstruct
    public void init() {
        generateMockData();
    }

    @Override
    public void generateMockData() {
        // One mock user for all GymDays
        User mockUser = User.builder()
                .id(1L)
                .username("gym_pro")
                .email("pro@gymbuddy.com")
                .gymDays(new ArrayList<>())
                .age(25)
                .height(180.5f)
                .weight(85.0f)
                .build();
        userRepository.save(mockUser);

        String[] exerciseNames = {"Bench Press", "Squat", "Deadlift", "Pull Ups", "Bicep Curls", "Overhead Press"};

        for (int i = 1; i <= 5; i++) {
            GymDay gymDay = GymDay.builder()
                    .id((long) i)
                    .date(LocalDate.now().minusDays(i))
                    .name("Workout " + i)
                    .user(mockUser)
                    .exercises(new ArrayList<>())
                    .build();

            int numExercises = random.nextInt(3) + 2; // 2 to 4 exercises
            for (int j = 1; j <= numExercises; j++) {
                Exercise exercise = Exercise.builder()
                        .id((long) (i * 10 + j))
                        .name(exerciseNames[random.nextInt(exerciseNames.length)])
                        .sets(new ArrayList<>())
                        .build();

                int numSets = random.nextInt(3) + 3; // 3 to 5 sets
                for (int k = 1; k <= numSets; k++) {
                    ExerciseSet set = ExerciseSet.builder()
                            .id((long) (i * 100 + j * 10 + k))
                            .setNumber(k)
                            .reps(random.nextInt(5) + 8) // 8 to 12 reps
                            .weight(random.nextInt(10) * 5 + 40) // 40 to 90 kg
                            .build();
                    
                    exerciseSetRepository.save(set);
                    exercise.getSets().add(set);
                }
                exerciseRepository.save(exercise);
                gymDay.getExercises().add(exercise);
            }
            gymDayRepository.save(gymDay);
            mockUser.getGymDays().add(gymDay);
        }
    }

    @Override
    public List<GymDay> getGymDays() {
        return gymDayRepository.findAll();
    }
}
