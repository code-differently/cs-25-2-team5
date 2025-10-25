package com.api.demo.services;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.api.demo.exceptions.EventNotFoundException;
import com.api.demo.models.EventModel;
import com.api.demo.repos.EventModelRepo;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
@DisplayName("Event Service Unit Tests")
public class EventServiceTest {

  @Mock private EventModelRepo eventModelRepo;

  @InjectMocks private EventService eventService;

  private EventModel publicEvent1;
  private EventModel publicEvent2;

  @BeforeEach
  void setUp() {
    publicEvent1 = new EventModel();
    publicEvent1.setId(1L);
    publicEvent1.setTitle("Public Event 1");
    publicEvent1.setIsPublic(true);

    publicEvent2 = new EventModel();
    publicEvent2.setId(2L);
    publicEvent2.setTitle("Public Event 2");
    publicEvent2.setIsPublic(true);
  }

  @Test
  @DisplayName("getCommunityEvents - Should return only public events")
  void getCommunityEvents_ShouldReturnOnlyPublicEvents() {
    // Given
    List<EventModel> publicEvents = Arrays.asList(publicEvent1, publicEvent2);
    when(eventModelRepo.findAllByIsPublicTrue()).thenReturn(publicEvents);

    // When
    Iterable<EventModel> result = eventService.getCommunityEvents();

    // Then
    assertNotNull(result);
    List<EventModel> resultList = (List<EventModel>) result;
    assertEquals(2, resultList.size());
    assertTrue(resultList.stream().allMatch(EventModel::getIsPublic));

    verify(eventModelRepo, times(1)).findAllByIsPublicTrue();
  }

  @Test
  @DisplayName("deleteEvent - Should delete existing event")
  void deleteEvent_ShouldDeleteEvent_WhenEventExists() {
    // Given
    Long eventId = 1L;
    when(eventModelRepo.findById(eventId)).thenReturn(Optional.of(publicEvent1));

    // When
    eventService.deleteEvent(eventId);

    // Then
    verify(eventModelRepo, times(1)).findById(eventId);
    verify(eventModelRepo, times(1)).delete(publicEvent1);
  }

  @Test
  @DisplayName("deleteEvent - Should throw exception when event not found")
  void deleteEvent_ShouldThrowException_WhenEventNotFound() {
    // Given
    Long eventId = 999L;
    when(eventModelRepo.findById(eventId)).thenReturn(Optional.empty());

    // When & Then
    assertThrows(
        EventNotFoundException.class,
        () -> {
          eventService.deleteEvent(eventId);
        });

    verify(eventModelRepo, times(1)).findById(eventId);
    verify(eventModelRepo, never()).delete(any());
  }
}
