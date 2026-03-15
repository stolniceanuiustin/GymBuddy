package com.gymbuddy.backend.service.impl;

import com.gymbuddy.backend.model.ExerciseSet;
import com.gymbuddy.backend.repository.ExerciseSetRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.time.LocalTime;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.initMocks;

class ExerciseSetServiceImplTest {
    private static final int SET_NUMBER = 1;
    private static final int REPS = 10;
    private static final double WEIGHT = 100.0;
    private ExerciseSet exerciseSet = new ExerciseSet(1L, SET_NUMBER, REPS, WEIGHT, LocalTime.now());

    //UUT = Unit Under Testing
    private ExerciseSetServiceImpl exerciseSetService;

    @Mock
    private ExerciseSetRepository exerciseSetRepository;

    @BeforeEach
    void setUp() {
        initMocks(this);
        exerciseSetService = new ExerciseSetServiceImpl(exerciseSetRepository);
    }

    @Test
    public void givenExistingSet_whenFindSetById_thenReturnSet() {
        // given
        when(exerciseSetRepository.findById(1L)).thenReturn(exerciseSet);

        // when
        ExerciseSet resultSet = exerciseSetService.getExerciseSetById(1L);

        // then
        assertNotNull(resultSet);
        assertEquals(SET_NUMBER, resultSet.getSetNumber());
        assertEquals(REPS, resultSet.getReps());
        assertEquals(WEIGHT, resultSet.getWeight());

        verify(exerciseSetRepository, times(1)).findById(1L);
        verify(exerciseSetRepository, times(0)).findAll();
    }

    @Test
    public void givenNonExistingSet_whenFindSetById_thenThrowException() {
        assertThrows(NoSuchElementException.class, () -> {
            exerciseSetService.getExerciseSetById(1L);
        });
    }

    @Test
    void givenValidSet_whenUpdateSet_thenReturnUpdatedSet() {
        // given
        when(exerciseSetRepository.findById(1L)).thenReturn(exerciseSet);
        ExerciseSet updatedSet = new ExerciseSet(1L, 2, 8, 105.0, LocalTime.now());
        when(exerciseSetRepository.updateExerciseSet(any(ExerciseSet.class))).thenReturn(updatedSet);

        // when
        ExerciseSet result = exerciseSetService.updateExerciseSet(updatedSet);

        // then
        assertNotNull(result);
        assertEquals(2, result.getSetNumber());
        assertEquals(8, result.getReps());
        assertEquals(105.0, result.getWeight());
    }
}