package com.api.demo.models;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.api.demo.controllers.EventGuestController;
import com.api.demo.services.EventGuestService;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
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

  private EventGuest testEventGuest;
  private EventModel testEvent;
  private User testUser;
  private EventGuestKey testEventGuestKey;

  @BeforeEach
  void setUp() {
    // Create test user
    testUser = new User();
    testUser.setId(1L);
    testUser.setName("Test User");
    testUser.setEmail("test@example.com");
    testUser.setPassword("password123");

    // Create test event
    testEvent = new EventModel();
    testEvent.setId(1L);
    testEvent.setTitle("Test Event");
    testEvent.setDescription("Test Description");
    testEvent.setStartTime(LocalDateTime.now().plusDays(1));
    testEvent.setOrganizer(testUser);
    testEvent.setIsPublic(false);

    // Create test event guest key
    testEventGuestKey = new EventGuestKey(1L, 1L);

    // Create test event guest
    testEventGuest =
        EventGuest.builder()
            .eventGuestKey(testEventGuestKey)
            .event(testEvent)
            .guest(testUser)
            .rsvpStatus(RsvpStatus.PENDING)
            .build();
  }

  @Nested
  @DisplayName("GET Endpoints Tests")
  class GetEndpointsTests {

    @Test
    @DisplayName("Should get guests by event ID")
    void shouldGetGuestsByEventId() {
      // Given
      Long eventId = 1L;
      List<EventGuest> expectedGuests = Arrays.asList(testEventGuest);
      when(eventGuestService.getGuestsByEventId(eventId)).thenReturn(expectedGuests);

      // When
      ResponseEntity<List<EventGuest>> response = eventGuestController.getGuestsByEventId(eventId);

      // Then
      assertEquals(HttpStatus.OK, response.getStatusCode());
      assertNotNull(response.getBody());
      assertEquals(1, response.getBody().size());
      assertEquals(testEventGuest, response.getBody().get(0));
      verify(eventGuestService).getGuestsByEventId(eventId);
    }

    @Test
    @DisplayName("Should get events by guest ID")
    void shouldGetEventsByGuestId() {
      // Given
      Long guestId = 1L;
      List<EventGuest> expectedEvents = Arrays.asList(testEventGuest);
      when(eventGuestService.getEventsByGuestId(guestId)).thenReturn(expectedEvents);

      // When
      ResponseEntity<List<EventGuest>> response = eventGuestController.getEventsByGuestId(guestId);

      // Then
      assertEquals(HttpStatus.OK, response.getStatusCode());
      assertNotNull(response.getBody());
      assertEquals(1, response.getBody().size());
      assertEquals(testEventGuest, response.getBody().get(0));
      verify(eventGuestService).getEventsByGuestId(guestId);
    }

    @Test
    @DisplayName("Should get specific event guest")
    void shouldGetEventGuest() {
      // Given
      Long eventId = 1L;
      Long guestId = 1L;
      when(eventGuestService.getEventGuest(eventId, guestId))
          .thenReturn(Optional.of(testEventGuest));

      // When
      ResponseEntity<EventGuest> response = eventGuestController.getEventGuest(eventId, guestId);

      // Then
      assertEquals(HttpStatus.OK, response.getStatusCode());
      assertNotNull(response.getBody());
      assertEquals(testEventGuest, response.getBody());
      verify(eventGuestService).getEventGuest(eventId, guestId);
    }

    @Test
    @DisplayName("Should return not found when event guest does not exist")
    void shouldReturnNotFoundWhenEventGuestDoesNotExist() {
      // Given
      Long eventId = 1L;
      Long guestId = 999L;
      when(eventGuestService.getEventGuest(eventId, guestId)).thenReturn(Optional.empty());

      // When
      ResponseEntity<EventGuest> response = eventGuestController.getEventGuest(eventId, guestId);

      // Then
      assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
      verify(eventGuestService).getEventGuest(eventId, guestId);
    }

    @Test
    @DisplayName("Should check if event guest exists")
    void shouldCheckEventGuestExists() {
      // Given
      Long eventId = 1L;
      Long guestId = 1L;
      when(eventGuestService.existsEventGuest(eventId, guestId)).thenReturn(true);

      // When
      ResponseEntity<Boolean> response =
          eventGuestController.checkEventGuestExists(eventId, guestId);

      // Then
      assertEquals(HttpStatus.OK, response.getStatusCode());
      assertNotNull(response.getBody());
      assertTrue(response.getBody());
      verify(eventGuestService).existsEventGuest(eventId, guestId);
    }
  }

  @Nested
  @DisplayName("POST Endpoints Tests")
  class PostEndpointsTests {

    @Test
    @DisplayName("Should create event with guests")
    void shouldCreateEventWithGuests() {
      // Given
      Long organizerId = 1L;
      Set<String> guestEmails = new HashSet<>();
      guestEmails.add("guest@example.com");
      when(eventGuestService.createEventWithGuests(organizerId, testEvent, guestEmails))
          .thenReturn(testEvent);

      // When
      ResponseEntity<EventModel> response =
          eventGuestController.createEventWithGuests(organizerId, testEvent, guestEmails);

      // Then
      assertEquals(HttpStatus.CREATED, response.getStatusCode());
      assertNotNull(response.getBody());
      assertEquals(testEvent, response.getBody());
      verify(eventGuestService).createEventWithGuests(organizerId, testEvent, guestEmails);
    }

    @Test
    @DisplayName("Should add guest to event")
    void shouldAddGuestToEvent() {
      // Given
      Long eventId = 1L;
      String guestEmail = "guest@example.com";
      when(eventGuestService.addNewGuestToEvent(eventId, guestEmail)).thenReturn(testEventGuest);

      // When
      ResponseEntity<EventGuest> response =
          eventGuestController.addGuestToEvent(eventId, guestEmail);

      // Then
      assertEquals(HttpStatus.CREATED, response.getStatusCode());
      assertNotNull(response.getBody());
      assertEquals(testEventGuest, response.getBody());
      verify(eventGuestService).addNewGuestToEvent(eventId, guestEmail);
    }

    @Test
    @DisplayName("Should save event guest")
    void shouldSaveEventGuest() {
      // Given
      when(eventGuestService.saveEventGuest(testEventGuest)).thenReturn(testEventGuest);

      // When
      ResponseEntity<EventGuest> response = eventGuestController.saveEventGuest(testEventGuest);

      // Then
      assertEquals(HttpStatus.CREATED, response.getStatusCode());
      assertNotNull(response.getBody());
      assertEquals(testEventGuest, response.getBody());
      verify(eventGuestService).saveEventGuest(testEventGuest);
    }
  }

  @Nested
  @DisplayName("PUT Endpoints Tests")
  class PutEndpointsTests {

    @Test
    @DisplayName("Should update RSVP status")
    void shouldUpdateRsvpStatus() {
      // Given
      Long eventId = 1L;
      Long guestId = 1L;
      RsvpStatus newStatus = RsvpStatus.ACCEPTED;
      when(eventGuestService.setStatus(any(EventGuestKey.class), any(RsvpStatus.class)))
          .thenReturn(newStatus);

      // When
      ResponseEntity<RsvpStatus> response =
          eventGuestController.updateRsvpStatus(eventId, guestId, newStatus);

      // Then
      assertEquals(HttpStatus.OK, response.getStatusCode());
      assertNotNull(response.getBody());
      assertEquals(newStatus, response.getBody());
      verify(eventGuestService).setStatus(any(EventGuestKey.class), any(RsvpStatus.class));
    }
  }

  @Nested
  @DisplayName("DELETE Endpoints Tests")
  class DeleteEndpointsTests {

    @Test
    @DisplayName("Should remove guest from event successfully")
    void shouldRemoveGuestFromEvent() {
      // Given
      Long eventId = 1L;
      String guestEmail = "guest@example.com";
      when(eventGuestService.removeGuestFromEvent(guestEmail, eventId)).thenReturn(true);

      // When
      ResponseEntity<Void> response =
          eventGuestController.removeGuestFromEvent(eventId, guestEmail);

      // Then
      assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
      verify(eventGuestService).removeGuestFromEvent(guestEmail, eventId);
    }

    @Test
    @DisplayName("Should return not found when guest removal fails")
    void shouldReturnNotFoundWhenGuestRemovalFails() {
      // Given
      Long eventId = 1L;
      String guestEmail = "guest@example.com";
      when(eventGuestService.removeGuestFromEvent(guestEmail, eventId)).thenReturn(false);

      // When
      ResponseEntity<Void> response =
          eventGuestController.removeGuestFromEvent(eventId, guestEmail);

      // Then
      assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
      verify(eventGuestService).removeGuestFromEvent(guestEmail, eventId);
    }
  }

  @Nested
  @DisplayName("Integration Tests")
  class IntegrationTests {

    @Test
    @DisplayName("Should handle complete workflow: create, add, update, remove")
    void shouldHandleCompleteWorkflow() {
      // Given
      Long organizerId = 1L;
      Long eventId = 1L;
      Long guestId = 1L;
      String guestEmail = "guest@example.com";
      Set<String> guestEmails = Set.of(guestEmail);

      // Setup mocks for workflow
      when(eventGuestService.createEventWithGuests(organizerId, testEvent, guestEmails))
          .thenReturn(testEvent);
      when(eventGuestService.addNewGuestToEvent(eventId, guestEmail)).thenReturn(testEventGuest);
      when(eventGuestService.setStatus(any(EventGuestKey.class), any(RsvpStatus.class)))
          .thenReturn(RsvpStatus.ACCEPTED);
      when(eventGuestService.removeGuestFromEvent(guestEmail, eventId)).thenReturn(true);

      // When & Then - Create event with guests
      ResponseEntity<EventModel> createResponse =
          eventGuestController.createEventWithGuests(organizerId, testEvent, guestEmails);
      assertEquals(HttpStatus.CREATED, createResponse.getStatusCode());

      // When & Then - Add guest to event
      ResponseEntity<EventGuest> addResponse =
          eventGuestController.addGuestToEvent(eventId, guestEmail);
      assertEquals(HttpStatus.CREATED, addResponse.getStatusCode());

      // When & Then - Update RSVP status
      ResponseEntity<RsvpStatus> updateResponse =
          eventGuestController.updateRsvpStatus(eventId, guestId, RsvpStatus.ACCEPTED);
      assertEquals(HttpStatus.OK, updateResponse.getStatusCode());

      // When & Then - Remove guest from event
      ResponseEntity<Void> removeResponse =
          eventGuestController.removeGuestFromEvent(eventId, guestEmail);
      assertEquals(HttpStatus.NO_CONTENT, removeResponse.getStatusCode());

      // Verify all service calls
      verify(eventGuestService).createEventWithGuests(organizerId, testEvent, guestEmails);
      verify(eventGuestService).addNewGuestToEvent(eventId, guestEmail);
      verify(eventGuestService).setStatus(any(EventGuestKey.class), any(RsvpStatus.class));
      verify(eventGuestService).removeGuestFromEvent(guestEmail, eventId);
    }
  }
}
