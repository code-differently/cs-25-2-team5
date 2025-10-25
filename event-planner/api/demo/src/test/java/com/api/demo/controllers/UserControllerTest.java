package com.api.demo.controllers;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.api.demo.exceptions.GlobalExceptionHandler;
import com.api.demo.exceptions.UserNotFoundException;
import com.api.demo.models.EventModel;
import com.api.demo.models.User;
import com.api.demo.services.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.time.LocalDateTime;
import java.util.HashSet;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@ExtendWith(MockitoExtension.class)
@DisplayName("UserController Tests")
public class UserControllerTest {

  @Mock private UserService userService;

  @InjectMocks private UserController userController;

  private MockMvc mockMvc;
  private ObjectMapper objectMapper;
  private User testUser;
  private EventModel testEvent;

  @BeforeEach
  void setUp() {
    mockMvc =
        MockMvcBuilders.standaloneSetup(userController)
            .setControllerAdvice(new GlobalExceptionHandler())
            .build();
    objectMapper = new ObjectMapper();
    objectMapper.findAndRegisterModules(); // Handle LocalDateTime serialization

    // Set up test user
    testUser = new User();
    testUser.setId(1L);
    testUser.setName("John Doe");
    testUser.setEmail("john@example.com");
    testUser.setPassword("password123");
    testUser.setOrganizedEvents(new HashSet<>());

    // Set up test event
    testEvent = new EventModel();
    testEvent.setId(1L);
    testEvent.setTitle("Test Event");
    testEvent.setDescription("Test Description");
    testEvent.setIsPublic(true);
    testEvent.setStartTime(LocalDateTime.now().plusDays(1));
    testEvent.setOrganizer(testUser);
  }

  @Test
  @DisplayName("GET /api/v1/users/{id} - Should return user when valid ID provided")
  void getUserById_ShouldReturnUser_WhenValidId() throws Exception {
    // Given
    Long userId = 1L;
    when(userService.getUserById(userId)).thenReturn(testUser);

    // When & Then
    mockMvc
        .perform(get("/api/v1/users/{id}", userId))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$.id").value(1))
        .andExpect(jsonPath("$.name").value("John Doe"))
        .andExpect(jsonPath("$.email").value("john@example.com"));
  }

  @Test
  @DisplayName("GET /api/v1/users/{id} - Should return 404 when user not found")
  void getUserById_ShouldReturn404_WhenUserNotFound() throws Exception {
    // Given
    Long nonExistentId = 999L;
    when(userService.getUserById(nonExistentId))
        .thenThrow(new UserNotFoundException("User not found"));

    // When & Then
    mockMvc.perform(get("/api/v1/users/{id}", nonExistentId)).andExpect(status().isNotFound());
  }

  @Test
  @DisplayName("POST /api/v1/users - Should create user successfully")
  void createUser_ShouldCreateUser_WhenValidData() throws Exception {
    // Given
    User userToCreate = new User();
    userToCreate.setName("Jane Smith");
    userToCreate.setEmail("jane@example.com");
    userToCreate.setPassword("securepass");

    User createdUser = new User();
    createdUser.setId(2L);
    createdUser.setName("Jane Smith");
    createdUser.setEmail("jane@example.com");
    createdUser.setPassword("securepass");

    when(userService.createUser(userToCreate)).thenReturn(createdUser);

    // When & Then
    mockMvc
        .perform(
            post("/api/v1/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(userToCreate)))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$.id").value(2))
        .andExpect(jsonPath("$.name").value("Jane Smith"))
        .andExpect(jsonPath("$.email").value("jane@example.com"));
  }

  @Test
  @DisplayName("POST /api/v1/users - Should handle invalid user data")
  void createUser_ShouldHandleInvalidData_WhenBadRequest() throws Exception {
    // Given - User with missing required fields
    User invalidUser = new User();
    // Missing name and email

    // When & Then
    mockMvc
        .perform(
            post("/api/v1/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(invalidUser)))
        .andExpect(status().isBadRequest());
  }

  @Test
  @DisplayName("POST /api/v1/users/{userId}/events - Should create event for user successfully")
  void createEventForUser_ShouldCreateEvent_WhenValidData() throws Exception {
    // Given
    Long userId = 1L;

    EventModel eventToCreate = new EventModel();
    eventToCreate.setTitle("New Event");
    eventToCreate.setDescription("New Event Description");
    eventToCreate.setIsPublic(true);
    eventToCreate.setStartTime(LocalDateTime.of(2025, 12, 25, 10, 0));

    EventModel createdEvent = new EventModel();
    createdEvent.setId(1L);
    createdEvent.setTitle("New Event");
    createdEvent.setDescription("New Event Description");
    createdEvent.setIsPublic(true);
    createdEvent.setStartTime(LocalDateTime.of(2025, 12, 25, 10, 0));
    createdEvent.setOrganizer(testUser);

    when(userService.createPublicEvent(eventToCreate, userId)).thenReturn(createdEvent);

    // When & Then
    mockMvc
        .perform(
            post("/api/v1/users/{userId}/events", userId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(eventToCreate)))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$.title").value("New Event"))
        .andExpect(jsonPath("$.description").value("New Event Description"))
        .andExpect(jsonPath("$.eventType").value("Community"))
        .andExpect(jsonPath("$.organizer.name").value("John Doe"))
        .andExpect(jsonPath("$.organizer.email").value("john@example.com"));
  }

  @Test
  @DisplayName("POST /api/v1/users/{userId}/events - Should create private event correctly")
  void createEventForUser_ShouldCreatePrivateEvent_WhenIsPublicFalse() throws Exception {
    // Given
    Long userId = 1L;

    EventModel privateEvent = new EventModel();
    privateEvent.setTitle("Private Event");
    privateEvent.setDescription("Private Description");
    privateEvent.setIsPublic(false); // Private event
    privateEvent.setStartTime(LocalDateTime.of(2025, 12, 25, 10, 0));

    EventModel createdPrivateEvent = new EventModel();
    createdPrivateEvent.setId(2L);
    createdPrivateEvent.setTitle("Private Event");
    createdPrivateEvent.setDescription("Private Description");
    createdPrivateEvent.setIsPublic(false);
    createdPrivateEvent.setStartTime(LocalDateTime.of(2025, 12, 25, 10, 0));
    createdPrivateEvent.setOrganizer(testUser);

    when(userService.createPublicEvent(privateEvent, userId)).thenReturn(createdPrivateEvent);

    // When & Then
    mockMvc
        .perform(
            post("/api/v1/users/{userId}/events", userId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(privateEvent)))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.title").value("Private Event"))
        .andExpect(jsonPath("$.eventType").value("Private"));
  }

  @Test
  @DisplayName("POST /api/v1/users/{userId}/events - Should return 404 when user not found")
  void createEventForUser_ShouldReturn404_WhenUserNotFound() throws Exception {
    // Given
    Long nonExistentUserId = 999L;
    EventModel event = new EventModel();
    event.setTitle("Test Event");

    when(userService.createPublicEvent(event, nonExistentUserId))
        .thenThrow(new UserNotFoundException("User not found"));

    // When & Then
    mockMvc
        .perform(
            post("/api/v1/users/{userId}/events", nonExistentUserId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(event)))
        .andExpect(status().isNotFound());
  }

  @Test
  @DisplayName("GET /api/v1/users/{id} - Should handle malformed ID")
  void getUserById_ShouldHandleMalformedId() throws Exception {
    // When & Then
    mockMvc.perform(get("/api/v1/users/{id}", "invalid-id")).andExpect(status().isBadRequest());
  }
}
