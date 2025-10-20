package com.api.demo.services;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.api.demo.exceptions.UserNotFoundException;
import com.api.demo.models.User;
import com.api.demo.repos.UserRepository;

@ExtendWith(MockitoExtension.class)
@DisplayName("UserService - getUserById Tests")
public class UserServiceTest {
    
    @Mock
    private UserRepository userRepository;
    
    @Mock
    private EventService eventService;
    
    @InjectMocks
    private UserService userService;
    
    private User testUser;
    
    @BeforeEach
    void setUp() {
        testUser = new User();
        testUser.setId(1);
        testUser.setName("John Doe");
        testUser.setEmail("john@example.com");
        testUser.setPassword("password123");
    }
    
    @Test
    @DisplayName("Should return user when valid ID is provided")
    void shouldReturnUser_WhenValidIdProvided() {
        // Given
        Integer userId = 1;
        when(userRepository.findById(userId)).thenReturn(Optional.of(testUser));
        
        // When
        User result = userService.getUserById(userId);
        
        // Then
        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(userId);
        assertThat(result.getName()).isEqualTo("John Doe");
        assertThat(result.getEmail()).isEqualTo("john@example.com");
    }
    
    @Test
    @DisplayName("Should throw UserNotFoundException when user ID does not exist")
    void shouldThrowUserNotFoundException_WhenUserIdDoesNotExist() {
        // Given
        Integer nonExistentId = 999;
        when(userRepository.findById(nonExistentId)).thenReturn(Optional.empty());
        
        // When & Then
        assertThatThrownBy(() -> userService.getUserById(nonExistentId))
            .isInstanceOf(UserNotFoundException.class)
            .hasMessage("User not found");
    }
    
    @Test
    @DisplayName("Should handle null ID appropriately")
    void shouldHandleNullId_Appropriately() {
        // Given
        Integer nullId = null;
        
        // When & Then - This will likely throw a different exception (like IllegalArgumentException)
        // depending on how Spring Data JPA handles null IDs, but we test that it doesn't succeed
        assertThatThrownBy(() -> userService.getUserById(nullId))
            .isInstanceOf(Exception.class); // Could be UserNotFoundException or IllegalArgumentException
    }
    
    @Test
    @DisplayName("Should return user with correct properties when user exists")
    void shouldReturnUserWithCorrectProperties_WhenUserExists() {
        // Given
        User expectedUser = new User();
        expectedUser.setId(42);
        expectedUser.setName("Jane Smith");
        expectedUser.setEmail("jane.smith@example.com");
        expectedUser.setPassword("securePassword");
        
        when(userRepository.findById(42)).thenReturn(Optional.of(expectedUser));
        
        // When
        User actualUser = userService.getUserById(42);
        
        // Then
        assertThat(actualUser)
            .usingRecursiveComparison()
            .isEqualTo(expectedUser);
    }
}
