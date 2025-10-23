package com.api.demo.services;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.api.demo.exceptions.UnauthorizedAccessException;
import com.api.demo.models.EventModel;
import com.api.demo.models.User;
import com.api.demo.repos.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class UserServiceUpdateEventTest {

  @Mock
  private UserRepository userRepository;

  @Mock
  private EventService eventService;

  @InjectMocks
  private UserService userService;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  void testUpdateUserEvent_WhenUserIsOrganizer_ShouldSucceed() {
    // Arrange
    Long userId = 1L;
    Long eventId = 1L;
    
    User user = new User();
    user.setId(userId);
    user.setName("Test User");
    user.setEmail("test@example.com");
    
    EventModel existingEvent = new EventModel();
    existingEvent.setId(eventId);
    existingEvent.setTitle("Original Event");
    existingEvent.setOrganizer(user);
    
    EventModel updatedEventInfo = new EventModel();
    updatedEventInfo.setTitle("Updated Event");
    updatedEventInfo.setDescription("Updated Description");
    
    EventModel updatedEvent = new EventModel();
    updatedEvent.setId(eventId);
    updatedEvent.setTitle("Updated Event");
    updatedEvent.setDescription("Updated Description");
    updatedEvent.setOrganizer(user);
    
    when(userRepository.findById(userId)).thenReturn(java.util.Optional.of(user));
    when(eventService.getEventById(eventId)).thenReturn(existingEvent);
    when(eventService.updateEvent(eventId, updatedEventInfo)).thenReturn(updatedEvent);
    
    // Act
    EventModel result = userService.updateUserEvent(userId, eventId, updatedEventInfo);
    
    // Assert
    assertEquals(updatedEvent, result);
    verify(eventService).updateEvent(eventId, updatedEventInfo);
  }

  @Test
  void testUpdateUserEvent_WhenUserIsNotOrganizer_ShouldThrowUnauthorizedException() {
    // Arrange
    Long userId = 1L;
    Long eventId = 1L;
    Long organizerId = 2L;
    
    User user = new User();
    user.setId(userId);
    user.setName("Test User");
    user.setEmail("test@example.com");
    
    User organizer = new User();
    organizer.setId(organizerId);
    organizer.setName("Organizer");
    organizer.setEmail("organizer@example.com");
    
    EventModel existingEvent = new EventModel();
    existingEvent.setId(eventId);
    existingEvent.setTitle("Original Event");
    existingEvent.setOrganizer(organizer); // Different organizer
    
    EventModel updatedEventInfo = new EventModel();
    updatedEventInfo.setTitle("Updated Event");
    
    when(userRepository.findById(userId)).thenReturn(java.util.Optional.of(user));
    when(eventService.getEventById(eventId)).thenReturn(existingEvent);
    
    // Act & Assert
    UnauthorizedAccessException exception = assertThrows(
        UnauthorizedAccessException.class, 
        () -> userService.updateUserEvent(userId, eventId, updatedEventInfo)
    );
    
    assertEquals("Only the event organizer can update this event", exception.getMessage());
    verify(eventService, never()).updateEvent(any(), any());
  }
}
