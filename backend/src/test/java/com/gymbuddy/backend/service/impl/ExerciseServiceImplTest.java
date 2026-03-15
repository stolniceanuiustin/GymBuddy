package com.gymbuddy.backend.service.impl;

import com.gymbuddy.backend.model.Exercise;
import com.gymbuddy.backend.repository.ExerciseRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.time.LocalTime;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.initMocks;

class ExerciseServiceImplTest {
    private static final String EXERCISE_NAME = "Bench Press";
    private Exercise exercise = new Exercise(1L, EXERCISE_NAME, LocalTime.now(), null);

    //UUT = Unit Under Testing
    private ExerciseServiceImpl exerciseService;

    @Mock
    private ExerciseRepository exerciseRepository;

    @BeforeEach
    void setUp() {
        initMocks(this);
        exerciseService = new ExerciseServiceImpl(exerciseRepository);
    }

    @Test
    public void givenExistingExercise_whenFindExerciseById_thenReturnExercise() {
        // given
        when(exerciseRepository.findById(1L)).thenReturn(exercise);

        // when
        Exercise resultExercise = exerciseService.getExerciseById(1L);

        // then
        assertNotNull(resultExercise);
        assertEquals(EXERCISE_NAME, resultExercise.getName());

        verify(exerciseRepository, times(1)).findById(1L);
        verify(exerciseRepository, times(0)).findAll();
    }

    @Test
    public void givenNonExistingExercise_whenFindExerciseById_thenThrowException() {
        assertThrows(NoSuchElementException.class, () -> {
            exerciseService.getExerciseById(1L);
        });
    }

    @Test
    void givenValidExercise_whenUpdateExercise_thenReturnUpdatedExercise() {
        // given
        when(exerciseRepository.findById(1L)).thenReturn(exercise);
        Exercise updatedExercise = new Exercise(1L, "Squat", LocalTime.now(), null);
        when(exerciseRepository.updateExercise(any(Exercise.class))).thenReturn(updatedExercise);

        // when
        Exercise result = exerciseService.updateExercise(updatedExercise);

        // then
        assertNotNull(result);
        assertEquals("Squat", result.getName());
    }
}