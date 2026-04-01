package com.gymbuddy.backend.service.impl;

import com.gymbuddy.backend.model.Role;
import com.gymbuddy.backend.model.User;
import com.gymbuddy.backend.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.initMocks;

class UserServiceImplTest {
    private static final String USERNAME = "TestUser";
    private User user = User.builder()
            .id(1L)
            .username(USERNAME)
            .email("test@email.com")
            .role(Role.STANDARD_USER)
            .gymDays(null)
            .age(20)
            .height(175.0f)
            .weight(70.0f)
            .build();

    //UUT = Unit Under Testing
    private UserServiceImpl userService;

    @Mock
    private UserRepository userRepository;

    @BeforeEach
    void setUp() {
        initMocks(this);
        userService = new UserServiceImpl(userRepository);
    }

    @Test
    public void givenExistingUser_whenFindUserById_thenReturnUser() {
        when(userRepository.findById(1L)).thenReturn(user);

        // when
        User resultUser = userService.getUserById(1L);

        // then
        assertNotNull(resultUser);
        assertEquals(USERNAME, resultUser.getUsername());
        assertEquals(Role.STANDARD_USER, resultUser.getRole());

        verify(userRepository, times(1)).findById(1L);
    }

    @Test
    public void givenNonExistingUser_whenFindUserById_thenThrowException() {
        assertThrows(NoSuchElementException.class, () -> {
            userService.getUserById(1L);
        });
    }

    @Test
    void givenValidUser_whenUpdateUser_thenReturnUpdatedUser() {
        // given
        when(userRepository.findById(1L)).thenReturn(user);
        User updatedUser = User.builder()
                .id(1L)
                .username("Updated Username")
                .email("updated@email.com")
                .role(Role.ADMINISTRATOR)
                .gymDays(null)
                .age(21)
                .height(176.0f)
                .weight(71.0f)
                .build();
        when(userRepository.updateUser(any(User.class))).thenReturn(updatedUser);

        // when
        User result = userService.updateUser(updatedUser);

        // then
        assertNotNull(result);
        assertEquals(Role.ADMINISTRATOR, result.getRole());
        assertEquals("Updated Username", result.getUsername());
    }
}
