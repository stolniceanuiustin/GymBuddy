package com.gymbuddy.backend.service.impl;

import com.gymbuddy.backend.dto.GymDayDTO;
import com.gymbuddy.backend.mapper.GymDayMapper;
import com.gymbuddy.backend.model.*;
import com.gymbuddy.backend.repository.*;
import com.gymbuddy.backend.service.MockDataService;
import jakarta.annotation.PostConstruct;
import net.datafaker.Faker;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@Service
public class MockDataServiceImpl implements MockDataService {
    private final Faker faker = new Faker();
    private final Random random = new Random();

    private final UserRepository userRepository;
    private final GymDayRepository gymDayRepository;
    private final ExerciseRepository exerciseRepository;
    private final ExerciseSetRepository exerciseSetRepository;
    private final ExerciseTypeRepository exerciseTypeRepository;
    private final WeightLogRepository weightLogRepository;
    private final GymDayMapper gymDayMapper;

    public MockDataServiceImpl(UserRepository userRepository,
                               GymDayRepository gymDayRepository,
                               ExerciseRepository exerciseRepository,
                               ExerciseSetRepository exerciseSetRepository,
                               ExerciseTypeRepository exerciseTypeRepository,
                               WeightLogRepository weightLogRepository,
                               GymDayMapper gymDayMapper) {
        this.userRepository = userRepository;
        this.gymDayRepository = gymDayRepository;
        this.exerciseRepository = exerciseRepository;
        this.exerciseSetRepository = exerciseSetRepository;
        this.exerciseTypeRepository = exerciseTypeRepository;
        this.weightLogRepository = weightLogRepository;
        this.gymDayMapper = gymDayMapper;
    }

    @PostConstruct
    public void init() {
        if (userRepository.count() == 0) {
            generateMockData();
        }
    }

    @Override
    public List<GymDayDTO> getGymDays() {
        return gymDayRepository.findAll().stream()
                .map(gymDayMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void generateMockData() {
        User admin = User.builder()
                .username("admin")
                .email("admin@gymbuddy.com")
                .password("password123")
                .role(Role.ADMINISTRATOR)
                .build();
        userRepository.save(admin);

        List<ExerciseType> catalog = new ArrayList<>();
        catalog.add(exerciseTypeRepository.save(ExerciseType.builder()
                .name("Bench Press").bodyweight(false).description("Chest exercise with weights").build()));
        catalog.add(exerciseTypeRepository.save(ExerciseType.builder()
                .name("Squat").bodyweight(false).description("Leg exercise with weights").build()));
        catalog.add(exerciseTypeRepository.save(ExerciseType.builder()
                .name("Pull Ups").bodyweight(true).description("Bodyweight back exercise").build()));
        catalog.add(exerciseTypeRepository.save(ExerciseType.builder()
                .name("Push Ups").bodyweight(true).description("Bodyweight chest exercise").build()));

        User mockUser = User.builder()
                .username("gym_pro")
                .email("pro@gymbuddy.com")
                .password("password123")
                .role(Role.STANDARD_USER)
                .age(25)
                .height(180.5f)
                .weight(85.0f)
                .build();
        userRepository.save(mockUser);

        float startingWeight = 90.0f;
        for (int i = 30; i >= 0; i--) {
            startingWeight -= (random.nextFloat() * 0.2f);
            weightLogRepository.save(WeightLog.builder()
                    .date(LocalDate.now().minusDays(i))
                    .weight(startingWeight + (random.nextFloat() * 0.5f))
                    .user(mockUser)
                    .build());
        }

        for (int i = 1; i <= 5; i++) {
            GymDay gymDay = GymDay.builder()
                    .date(LocalDate.now().minusDays(i))
                    .name("Workout " + i)
                    .user(mockUser)
                    .exercises(new ArrayList<>())
                    .notes("Good session today " + i)
                    .sleepQuality(random.nextInt(10) + 1)
                    .energyLevel(random.nextInt(10) + 1)
                    .build();

            int numExercises = random.nextInt(3) + 2;
            for (int j = 1; j <= numExercises; j++) {
                ExerciseType randomType = catalog.get(random.nextInt(catalog.size()));
                Exercise exercise = Exercise.builder()
                        .exerciseType(randomType)
                        .sets(new ArrayList<>())
                        .build();

                int numSets = random.nextInt(3) + 3;
                for (int k = 1; k <= numSets; k++) {
                    Double weight = (randomType.isBodyweight()) ? null : (double) (random.nextInt(10) * 5 + 40);
                    ExerciseSet set = ExerciseSet.builder()
                            .setNumber(k)
                            .reps(random.nextInt(5) + 8)
                            .weight(weight)
                            .build();
                    
                    exerciseSetRepository.save(set);
                    exercise.getSets().add(set);
                }
                exerciseRepository.save(exercise);
                gymDay.getExercises().add(exercise);
            }
            gymDayRepository.save(gymDay);
        }
    }
}
