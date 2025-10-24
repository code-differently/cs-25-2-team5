package com.api.demo.services;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

import com.api.demo.exceptions.EventNotFoundException;
import com.api.demo.models.EventModel;
import com.api.demo.repos.EventModelRepo;
import java.time.LocalDateTime;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class EventServiceUpdateTest {

  @Mock private EventModelRepo eventModelRepo;

  @InjectMocks private EventService eventService;

  private EventModel existingEvent;
  private EventModel updateInfo;

  @BeforeEach
  void setUp() {
    existingEvent = new EventModel();
    existingEvent.setId(1L);
    existingEvent.setTitle("Original Event");
    existingEvent.setDescription("Original Description");
    existingEvent.setStartTime(LocalDateTime.now());
    existingEvent.setIsPublic(true);

    updateInfo = new EventModel();
    updateInfo.setTitle("Updated Event");
    updateInfo.setDescription("Updated Description");
  }

  @Test
  void testUpdateEvent_Success() {
    // Given
    when(eventModelRepo.findById(1L)).thenReturn(Optional.of(existingEvent));
    when(eventModelRepo.save(any(EventModel.class))).thenReturn(existingEvent);

    // When
    EventModel result = eventService.updateEvent(1L, updateInfo);

    // Then
    assertEquals("Updated Event", result.getTitle());
    assertEquals("Updated Description", result.getDescription());
  }

  @Test
  void testUpdateEvent_EventNotFound() {
    // Given
    when(eventModelRepo.findById(anyLong())).thenReturn(Optional.empty());

    // When & Then
    assertThrows(
        EventNotFoundException.class,
        () -> {
          eventService.updateEvent(999L, updateInfo);
        });
  }

  @Test
  void testUpdateEvent_PartialUpdate() {
    // Given - only update title, leave description unchanged
    EventModel partialUpdate = new EventModel();
    partialUpdate.setTitle("New Title Only");

    when(eventModelRepo.findById(1L)).thenReturn(Optional.of(existingEvent));
    when(eventModelRepo.save(any(EventModel.class))).thenReturn(existingEvent);

    // When
    EventModel result = eventService.updateEvent(1L, partialUpdate);

    // Then
    assertEquals("New Title Only", result.getTitle());
    assertEquals("Original Description", result.getDescription()); // Should remain unchanged
  }
}
