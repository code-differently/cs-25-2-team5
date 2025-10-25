package com.api.demo.controllers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.api.demo.dtos.CreateEventWithGuestsRequest;
import com.api.demo.models.EventGuest;
import com.api.demo.models.EventGuestKey;
import com.api.demo.models.EventModel;
import com.api.demo.models.RsvpStatus;
import com.api.demo.services.EventGuestService;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@ExtendWith(MockitoExtension.class)
@DisplayName("EventGuestController Tests")
class EventGuestControllerTest {

  @Mock private EventGuestService eventGuestService;

  @InjectMocks private EventGuestController eventGuestController;

  @Test
  @DisplayName("Should create EventGuestController successfully")
  void shouldCreateEventGuestController() {
    // Given - controller is injected by Mockito

    // When - we check if controller exists

    // Then - controller should not be null
    assertNotNull(eventGuestController);
  }

  @Test
  @DisplayName("Should test basic service method calls")
  void shouldTestBasicServiceMethodCalls() {
    // Given
    Long eventId = 1L;
    List<EventGuest> mockGuests = Arrays.asList(); // Empty list for now
    when(eventGuestService.getGuestsByEventId(eventId)).thenReturn(mockGuests);

    // When
    ResponseEntity<List<EventGuest>> response = eventGuestController.getGuestsByEventId(eventId);

    // Then
    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertNotNull(response.getBody());
    verify(eventGuestService).getGuestsByEventId(eventId);
  }

  @Test
  @DisplayName("Should test createEventWithGuests method")
  void shouldTestCreateEventWithGuests() {
    // Given
    Long organizerId = 1L;
    EventModel mockEvent = new EventModel(); // Will use default constructor
    Set<String> guestEmails = new HashSet<>();
    guestEmails.add("test@example.com");

    CreateEventWithGuestsRequest request = new CreateEventWithGuestsRequest(mockEvent, guestEmails);

    when(eventGuestService.createEventWithGuests(organizerId, mockEvent, guestEmails))
        .thenReturn(mockEvent);

    // When
    ResponseEntity<EventModel> response =
        eventGuestController.createEventWithGuests(organizerId, request);

    // Then
    assertEquals(HttpStatus.CREATED, response.getStatusCode());
    assertNotNull(response.getBody());
    verify(eventGuestService).createEventWithGuests(organizerId, mockEvent, guestEmails);
  }

  @Test
  @DisplayName("Should test addGuestToEvent method")
  void shouldTestAddGuestToEvent() {
    // Given
    Long eventId = 1L;
    String guestEmail = "guest@example.com";
    EventGuest mockEventGuest = new EventGuest(); // Will use default constructor

    when(eventGuestService.addNewGuestToEvent(eventId, guestEmail)).thenReturn(mockEventGuest);

    // When
    ResponseEntity<EventGuest> response = eventGuestController.addGuestToEvent(eventId, guestEmail);

    // Then
    assertEquals(HttpStatus.CREATED, response.getStatusCode());
    assertNotNull(response.getBody());
    verify(eventGuestService).addNewGuestToEvent(eventId, guestEmail);
  }

  @Test
  @DisplayName("Should test updateRsvpStatus method")
  void shouldTestUpdateRsvpStatus() {
    // Given
    Long eventId = 1L;
    Long guestId = 1L;
    RsvpStatus status = RsvpStatus.ACCEPTED;

    when(eventGuestService.setStatus(any(EventGuestKey.class), any(RsvpStatus.class)))
        .thenReturn(status);

    // When
    ResponseEntity<RsvpStatus> response =
        eventGuestController.updateRsvpStatus(eventId, guestId, status);

    // Then
    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertEquals(status, response.getBody());
    verify(eventGuestService).setStatus(any(EventGuestKey.class), any(RsvpStatus.class));
  }

  @Test
  @DisplayName("Should test removeGuestFromEvent success")
  void shouldTestRemoveGuestFromEventSuccess() {
    // Given
    Long eventId = 1L;
    String guestEmail = "guest@example.com";

    when(eventGuestService.removeGuestFromEvent(guestEmail, eventId)).thenReturn(true);

    // When
    ResponseEntity<Void> response = eventGuestController.removeGuestFromEvent(eventId, guestEmail);

    // Then
    assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    verify(eventGuestService).removeGuestFromEvent(guestEmail, eventId);
  }

  @Test
  @DisplayName("Should test removeGuestFromEvent not found")
  void shouldTestRemoveGuestFromEventNotFound() {
    // Given
    Long eventId = 1L;
    String guestEmail = "guest@example.com";

    when(eventGuestService.removeGuestFromEvent(guestEmail, eventId)).thenReturn(false);

    // When
    ResponseEntity<Void> response = eventGuestController.removeGuestFromEvent(eventId, guestEmail);

    // Then
    assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    verify(eventGuestService).removeGuestFromEvent(guestEmail, eventId);
  }
}
