package com.gymbuddy.backend.service.impl;

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
    private User user = new User(1L, USERNAME, "test@email.com", null);

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

        verify(userRepository, times(1)).findById(1L);
        verify(userRepository, times(0)).findAll();
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
        User updatedUser = new User(1L, "Updated Username", "updated@email.com", null);
        when(userRepository.updateUser(any(User.class))).thenReturn(updatedUser);

        // when
        User result = userService.updateUser(updatedUser);

        // then
        assertNotNull(result);
        assertEquals("Updated Username", result.getUsername());
        assertEquals("updated@email.com", result.getEmail());
    }
}