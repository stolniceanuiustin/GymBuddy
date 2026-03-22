package com.gymbuddy.backend.service.impl;

import com.gymbuddy.backend.model.GymDay;
import com.gymbuddy.backend.repository.GymDayRepository;
import com.gymbuddy.backend.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.time.LocalDate;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.initMocks;

class GymDayServiceImplTest {
    private static final String GYM_DAY_NAME = "Push Day";
    private GymDay gymDay = new GymDay(1L, LocalDate.now(), GYM_DAY_NAME, null, null);

    //UUT = Unit Under Testing
    private GymDayServiceImpl gymDayService;

    @Mock
    private GymDayRepository gymDayRepository;
    @Mock
    private UserRepository userRepository;

    @BeforeEach
    void setUp() {
        initMocks(this);
        gymDayService = new GymDayServiceImpl(gymDayRepository, userRepository);
    }

    @Test
    public void givenExistingGymDay_whenFindGymDayById_thenReturnGymDay() {
        // given
        when(gymDayRepository.findById(1L)).thenReturn(gymDay);

        // when
        GymDay resultGymDay = gymDayService.getGymDayById(1L);

        // then
        assertNotNull(resultGymDay);
        assertEquals(GYM_DAY_NAME, resultGymDay.getName());

        verify(gymDayRepository, times(1)).findById(1L);
        verify(gymDayRepository, times(0)).findAll();
    }

    @Test
    public void givenNonExistingGymDay_whenFindGymDayById_thenThrowException() {
        assertThrows(NoSuchElementException.class, () -> {
            gymDayService.getGymDayById(1L);
        });
    }

    @Test
    void givenValidGymDay_whenUpdateGymDay_thenReturnUpdatedGymDay() {
        // given
        when(gymDayRepository.findById(1L)).thenReturn(gymDay);
        GymDay updatedGymDay = new GymDay(1L, LocalDate.now(), "Pull Day", null, null);
        when(gymDayRepository.updateGymDay(any(GymDay.class))).thenReturn(updatedGymDay);

        // when
        GymDay result = gymDayService.updateGymDay(updatedGymDay);

        // then
        assertNotNull(result);
        assertEquals("Pull Day", result.getName());
    }
}