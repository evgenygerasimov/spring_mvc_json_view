package com.spring_mvc_json_view.service;

import com.spring_mvc_json_view.entity.User;
import com.spring_mvc_json_view.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    private User user;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        user = new User();
        user.setId(1L);
        user.setName("John Doe");
        user.setEmail("john.doe@example.com");
    }

    @Test
    void shouldReturnAllUsersTest() {
        when(userRepository.findAll()).thenReturn(List.of(user));

        List<User> users = userService.getAllUsers();

        assertNotNull(users);
        assertEquals(1, users.size());
        assertEquals("John Doe", users.get(0).getName());
        verify(userRepository, times(1)).findAll();
    }

    @Test
    void shouldReturnUserByIdTest() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        Optional<User> result = userService.getUserById(1L);

        assertTrue(result.isPresent());
        assertEquals("John Doe", result.get().getName());
        verify(userRepository, times(1)).findById(1L);
    }

    @Test
    void shouldCreateUserTest() {
        when(userRepository.save(user)).thenReturn(user);

        User result = userService.createUser(user);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("John Doe", result.getName());
        verify(userRepository, times(1)).save(user);
    }

    @Test
    void shouldUpdateUserTest() {
        user.setId(1L);
        user.setName("Updated User");
        user.setEmail("updated@example.com");
        when(userRepository.existsById(1L)).thenReturn(true);
        when(userRepository.save(user)).thenReturn(user);

        User result = userService.updateUser(1L, user);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("Updated User", result.getName());
        verify(userRepository, times(1)).existsById(1L);
        verify(userRepository, times(1)).save(user);
    }

    @Test
    void shouldThrowExceptionWhenUpdateUserNotFoundTest() {
        user.setName("Updated user");
        when(userRepository.existsById(1L)).thenReturn(false);


        RuntimeException exception = assertThrows(RuntimeException.class, () -> userService.updateUser(1L, user));
        assertEquals("User not found", exception.getMessage());
        verify(userRepository, times(1)).existsById(1L);
        verify(userRepository, never()).save(any());
    }

    @Test
    void shouldDeleteUserTest() {
        doNothing().when(userRepository).deleteById(1L);

        userService.deleteUser(1L);

        verify(userRepository, times(1)).deleteById(1L);
    }
}
