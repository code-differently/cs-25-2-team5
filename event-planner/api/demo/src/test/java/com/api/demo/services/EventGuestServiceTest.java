package com.api.demo.services;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anySet;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.api.demo.exceptions.EventGuestNotFoundException;
import com.api.demo.exceptions.UserNotFoundException;
import com.api.demo.models.EventGuest;
import com.api.demo.models.EventGuestKey;
import com.api.demo.models.EventModel;
import com.api.demo.models.RsvpStatus;
import com.api.demo.models.User;
import com.api.demo.repos.EventGuestRepo;
import java.time.LocalDateTime;
import java.util.HashSet;
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

@ExtendWith(MockitoExtension.class)
@DisplayName("EventGuestService Tests")
class EventGuestServiceTest {

  @Mock private EventGuestRepo eventGuestRepo;
  @Mock private UserService userService;
  @Mock private EventService eventService;
  @InjectMocks private EventGuestService eventGuestService;

  private User testUser;
  private User testOrganizer;
  private User testGuest1;
  private User testGuest2;
  private EventModel testEvent;
  private EventGuest testEventGuest;
  private EventGuestKey testEventGuestKey;
  private Set<String> testEmails;
  private Set<User> testUsers;

  @BeforeEach
  void setUp() {
    // Create test organizer
    testOrganizer = new User();
    testOrganizer.setId(1L);
    testOrganizer.setName("John Organizer");
    testOrganizer.setEmail("organizer@example.com");
    testOrganizer.setPassword("password123");
    testOrganizer.setOrganizedEvents(new HashSet<>());

    // Create test user (main guest)
    testUser = new User();
    testUser.setId(2L);
    testUser.setName("Jane Guest");
    testUser.setEmail("guest@example.com");
    testUser.setPassword("password123");
    testUser.setEventGuests(new HashSet<>());

    // Create additional test guests
    testGuest1 = new User();
    testGuest1.setId(2L);
    testGuest1.setName("Jane Guest");
    testGuest1.setEmail("guest1@example.com");
    testGuest1.setPassword("password123");
    testGuest1.setEventGuests(new HashSet<>());

    testGuest2 = new User();
    testGuest2.setId(3L);
    testGuest2.setName("Bob Guest");
    testGuest2.setEmail("guest2@example.com");
    testGuest2.setPassword("password123");
    testGuest2.setEventGuests(new HashSet<>());

    // Create test event
    testEvent = new EventModel();
    testEvent.setId(1L);
    testEvent.setTitle("Test Event");
    testEvent.setDescription("Test Description");
    testEvent.setStartTime(LocalDateTime.now().plusDays(1));
    testEvent.setOrganizer(testOrganizer);
    testEvent.setIsPublic(true); // Will be set to false by the method
    testEvent.setEventGuests(new HashSet<>());

    // Create test EventGuestKey
    testEventGuestKey = new EventGuestKey(1L, 2L);

    // Create test EventGuest
    testEventGuest =
        EventGuest.builder()
            .eventGuestKey(testEventGuestKey)
            .event(testEvent)
            .guest(testUser)
            .rsvpStatus(RsvpStatus.PENDING)
            .build();

    // Create test email set
    testEmails = new HashSet<>();
    testEmails.add("guest@example.com");
    testEmails.add("guest2@example.com");

    // Create test users set
    testUsers = new HashSet<>();
    testUsers.add(testUser);
    testUsers.add(testGuest2);
  }

  @Nested
  @DisplayName("createEventWithGuests Tests")
  class CreateEventWithGuestsTests {

    @Test
    @DisplayName("Should create event with guests successfully")
    void shouldCreateEventWithGuestsSuccessfully() {
      // Given
      Long organizerId = 1L;
      when(userService.getUserById(organizerId)).thenReturn(testOrganizer);
      when(userService.getAllUsersFromEmails(testEmails)).thenReturn(testUsers);
      when(eventGuestRepo.save(any(EventGuest.class))).thenReturn(testEventGuest);
      when(eventService.createEvent(any(EventModel.class))).thenReturn(testEvent);

      // When
      EventModel result =
          eventGuestService.createEventWithGuests(organizerId, testEvent, testEmails);

      // Then
      assertThat(result).isNotNull();
      assertThat(result.getIsPublic()).isFalse();
      assertThat(result.getOrganizer()).isEqualTo(testOrganizer);
      assertEquals("Test Event", result.getTitle());
      assertEquals("Test Description", result.getDescription());

      verify(userService).getUserById(organizerId);
      verify(userService).getAllUsersFromEmails(testEmails);
      verify(eventGuestRepo, times(2)).save(any(EventGuest.class)); // 2 users in testUsers
      verify(eventService).createEvent(testEvent);
    }

    @Test
    @DisplayName("Should set correct RSVP status for all guests")
    void shouldSetPendingRsvpStatusForAllGuests() {
      // Given
      Long organizerId = 1L;
      Set<User> guestUsers = Set.of(testGuest1, testGuest2);

      EventModel expectedCreatedEvent = new EventModel();
      expectedCreatedEvent.setId(1L);
      expectedCreatedEvent.setOrganizer(testOrganizer);

      when(userService.getUserById(organizerId)).thenReturn(testOrganizer);
      when(userService.getAllUsersFromEmails(testEmails)).thenReturn(guestUsers);
      when(eventService.createEvent(any(EventModel.class))).thenReturn(expectedCreatedEvent);

      // When
      EventModel result =
          eventGuestService.createEventWithGuests(organizerId, testEvent, testEmails);

      // Then
      assertNotNull(result);

      // Verify that event guests were created with PENDING status
      verify(eventGuestRepo, times(2)).save(any(EventGuest.class));
    }

    @Test
    @DisplayName("Should handle single guest correctly")
    void shouldCreateEventWithSingleGuest() {
      // Given
      Long organizerId = 1L;
      Set<String> singleGuestEmail = Set.of("guest1@example.com");
      Set<User> singleGuestUser = Set.of(testGuest1);

      EventModel expectedCreatedEvent = new EventModel();
      expectedCreatedEvent.setId(1L);
      expectedCreatedEvent.setOrganizer(testOrganizer);

      when(userService.getUserById(organizerId)).thenReturn(testOrganizer);
      when(userService.getAllUsersFromEmails(singleGuestEmail)).thenReturn(singleGuestUser);
      when(eventService.createEvent(any(EventModel.class))).thenReturn(expectedCreatedEvent);

      // When
      EventModel result =
          eventGuestService.createEventWithGuests(organizerId, testEvent, singleGuestEmail);

      // Then
      assertNotNull(result);
      assertEquals(testOrganizer, result.getOrganizer());

      // Verify only one guest was saved
      verify(eventGuestRepo, times(1)).save(any(EventGuest.class));
      verify(userService).getUserById(organizerId);
      verify(userService).getAllUsersFromEmails(singleGuestEmail);
      verify(eventService).createEvent(any(EventModel.class));
    }

    @Test
    @DisplayName("Should handle empty email set")
    void shouldHandleEmptyEmailSet() {
      // Given
      Long organizerId = 1L;
      Set<String> emptyEmails = new HashSet<>();
      Set<User> emptyUsers = new HashSet<>();

      when(userService.getUserById(organizerId)).thenReturn(testOrganizer);
      when(userService.getAllUsersFromEmails(emptyEmails)).thenReturn(emptyUsers);
      when(eventService.createEvent(any(EventModel.class))).thenReturn(testEvent);

      // When
      EventModel result =
          eventGuestService.createEventWithGuests(organizerId, testEvent, emptyEmails);

      // Then
      assertThat(result).isNotNull();
      assertFalse(result.getIsPublic()); // Should still be set to false
      verify(eventGuestRepo, never()).save(any(EventGuest.class));
      verify(eventService).createEvent(testEvent);
    }

    @Test
    @DisplayName("Should set event to private (isPublic = false)")
    void shouldSetEventToPrivate() {
      // Given
      Long organizerId = 1L;
      Set<User> guestUsers = Set.of(testGuest1);

      // Start with a public event
      testEvent.setIsPublic(true);

      EventModel expectedCreatedEvent = new EventModel();
      expectedCreatedEvent.setId(1L);
      expectedCreatedEvent.setIsPublic(false);
      expectedCreatedEvent.setOrganizer(testOrganizer);

      when(userService.getUserById(organizerId)).thenReturn(testOrganizer);
      when(userService.getAllUsersFromEmails(anySet())).thenReturn(guestUsers);
      when(eventService.createEvent(any(EventModel.class))).thenReturn(expectedCreatedEvent);

      // When
      EventModel result =
          eventGuestService.createEventWithGuests(organizerId, testEvent, testEmails);

      // Then
      assertNotNull(result);
      assertFalse(result.getIsPublic()); // Should be set to false regardless of original value
    }

    @Test
    @DisplayName("Should set organizer correctly")
    void shouldSetOrganizerCorrectly() {
      // Given
      Long organizerId = 1L;
      Set<User> guestUsers = Set.of(testGuest1);

      EventModel expectedCreatedEvent = new EventModel();
      expectedCreatedEvent.setId(1L);
      expectedCreatedEvent.setOrganizer(testOrganizer);

      when(userService.getUserById(organizerId)).thenReturn(testOrganizer);
      when(userService.getAllUsersFromEmails(anySet())).thenReturn(guestUsers);
      when(eventService.createEvent(any(EventModel.class))).thenReturn(expectedCreatedEvent);

      // When
      EventModel result =
          eventGuestService.createEventWithGuests(organizerId, testEvent, testEmails);

      // Then
      assertNotNull(result);
      assertEquals(testOrganizer, result.getOrganizer());
      assertEquals("John Organizer", result.getOrganizer().getName());
      assertEquals("organizer@example.com", result.getOrganizer().getEmail());

      verify(userService).getUserById(organizerId);
    }
  }

  @Nested
  @DisplayName("addNewGuestToEvent Tests")
  class AddNewGuestToEventTests {

    @Test
    @DisplayName("Should add new guest to event successfully")
    void shouldAddNewGuestToEventSuccessfully() {
      // Given
      Long eventId = 1L;
      String guestEmail = "guest@example.com";

      when(eventService.getEventById(eventId)).thenReturn(testEvent);
      when(userService.getUserByEmail(guestEmail)).thenReturn(testUser);
      when(eventGuestRepo.save(any(EventGuest.class))).thenReturn(testEventGuest);

      // When
      EventGuest result = eventGuestService.addNewGuestToEvent(eventId, guestEmail);

      // Then
      assertThat(result).isNotNull();
      assertThat(result.getRsvpStatus()).isEqualTo(RsvpStatus.PENDING);
      assertThat(result.getEvent()).isEqualTo(testEvent);
      assertThat(result.getGuest()).isEqualTo(testUser);

      verify(eventService).getEventById(eventId);
      verify(userService).getUserByEmail(guestEmail);
      verify(eventGuestRepo).save(any(EventGuest.class));
    }

    @Test
    @DisplayName("Should handle event not found")
    void shouldHandleEventNotFound() {
      // Given
      Long eventId = 999L;
      String guestEmail = "guest@example.com";

      when(eventService.getEventById(eventId)).thenThrow(new RuntimeException("Event not found"));

      // When & Then
      assertThatThrownBy(() -> eventGuestService.addNewGuestToEvent(eventId, guestEmail))
          .isInstanceOf(RuntimeException.class)
          .hasMessage("Event not found");

      verify(eventService).getEventById(eventId);
      verify(userService, never()).getUserByEmail(anyString());
      verify(eventGuestRepo, never()).save(any(EventGuest.class));
    }
  }

  @Nested
  @DisplayName("removeGuestFromEvent Tests")
  class RemoveGuestFromEventTests {

    @Test
    @DisplayName("Should remove guest from event successfully")
    void shouldRemoveGuestFromEventSuccessfully() {
      // Given
      String guestEmail = "guest@example.com";
      Long eventId = 1L;

      when(userService.getUserByEmail(guestEmail)).thenReturn(testUser);
      when(eventGuestRepo.existsById(any(EventGuestKey.class))).thenReturn(true);

      // When
      Boolean result = eventGuestService.removeGuestFromEvent(guestEmail, eventId);

      // Then
      assertThat(result).isTrue();

      verify(userService).getUserByEmail(guestEmail);
      verify(eventGuestRepo).existsById(any(EventGuestKey.class));
      verify(eventGuestRepo).deleteById(any(EventGuestKey.class));
    }

    @Test
    @DisplayName("Should return false when guest not found in event")
    void shouldReturnFalseWhenGuestNotFoundInEvent() {
      // Given
      String guestEmail = "guest@example.com";
      Long eventId = 1L;

      when(userService.getUserByEmail(guestEmail)).thenReturn(testUser);
      when(eventGuestRepo.existsById(any(EventGuestKey.class))).thenReturn(false);

      // When
      Boolean result = eventGuestService.removeGuestFromEvent(guestEmail, eventId);

      // Then
      assertThat(result).isFalse();

      verify(userService).getUserByEmail(guestEmail);
      verify(eventGuestRepo).existsById(any(EventGuestKey.class));
      verify(eventGuestRepo, never()).deleteById(any(EventGuestKey.class));
    }

    @Test
    @DisplayName("Should throw exception when user not found")
    void shouldThrowExceptionWhenUserNotFound() {
      // Given
      String guestEmail = "guest@example.com";
      Long eventId = 1L;

      when(userService.getUserByEmail(guestEmail))
          .thenThrow(new UserNotFoundException("User not found"));

      // When & Then
      assertThatThrownBy(() -> eventGuestService.removeGuestFromEvent(guestEmail, eventId))
          .isInstanceOf(UserNotFoundException.class)
          .hasMessage("User not found");

      verify(userService).getUserByEmail(guestEmail);
      verify(eventGuestRepo, never()).existsById(any(EventGuestKey.class));
      verify(eventGuestRepo, never()).deleteById(any(EventGuestKey.class));
    }
  }

  @Nested
  @DisplayName("getEventGuest Tests")
  class GetEventGuestTests {

    @Test
    @DisplayName("Should get event guest successfully")
    void shouldGetEventGuestSuccessfully() {
      // Given
      Long eventId = 1L;
      Long guestId = 2L;

      when(eventGuestRepo.findById(testEventGuestKey)).thenReturn(Optional.of(testEventGuest));

      // When
      EventGuest result = eventGuestService.getEventGuest(eventId, guestId);

      // Then
      assertThat(result).isNotNull();
      assertThat(result).isEqualTo(testEventGuest);
      assertThat(result.getEventGuestKey()).isEqualTo(testEventGuestKey);
      assertThat(result.getRsvpStatus()).isEqualTo(RsvpStatus.PENDING);

      verify(eventGuestRepo).findById(testEventGuestKey);
    }

    @Test
    @DisplayName("Should throw exception when event guest not found")
    void shouldThrowExceptionWhenEventGuestNotFound() {
      // Given
      Long eventId = 1L;
      Long guestId = 999L;
      EventGuestKey notFoundKey = new EventGuestKey(eventId, guestId);

      when(eventGuestRepo.findById(notFoundKey)).thenReturn(Optional.empty());

      // When & Then
      assertThatThrownBy(() -> eventGuestService.getEventGuest(eventId, guestId))
          .isInstanceOf(EventGuestNotFoundException.class)
          .hasMessage("EventGuest not found with key: " + notFoundKey);

      verify(eventGuestRepo).findById(notFoundKey);
    }
  }

  @Nested
  @DisplayName("setStatus Tests")
  class SetStatusTests {

    @Test
    @DisplayName("Should update RSVP status successfully")
    void shouldUpdateRsvpStatusSuccessfully() {
      // Given
      RsvpStatus newStatus = RsvpStatus.ACCEPTED;
      EventGuest updatedEventGuest =
          EventGuest.builder()
              .eventGuestKey(testEventGuestKey)
              .event(testEvent)
              .guest(testUser)
              .rsvpStatus(newStatus)
              .build();

      when(eventGuestRepo.findById(testEventGuestKey)).thenReturn(Optional.of(testEventGuest));
      when(eventGuestRepo.save(any(EventGuest.class))).thenReturn(updatedEventGuest);

      // When
      RsvpStatus result = eventGuestService.setStatus(testEventGuestKey, newStatus);

      // Then
      assertThat(result).isEqualTo(newStatus);

      verify(eventGuestRepo).findById(testEventGuestKey);
      verify(eventGuestRepo).save(any(EventGuest.class));
    }

    @Test
    @DisplayName("Should throw exception when EventGuest not found")
    void shouldThrowExceptionWhenEventGuestNotFound() {
      // Given
      RsvpStatus newStatus = RsvpStatus.ACCEPTED;

      when(eventGuestRepo.findById(testEventGuestKey)).thenReturn(Optional.empty());

      // When & Then
      assertThatThrownBy(() -> eventGuestService.setStatus(testEventGuestKey, newStatus))
          .isInstanceOf(EventGuestNotFoundException.class)
          .hasMessage("EventGuest not found with key: " + testEventGuestKey);

      verify(eventGuestRepo).findById(testEventGuestKey);
      verify(eventGuestRepo, never()).save(any(EventGuest.class));
    }

    @Test
    @DisplayName("Should handle all RSVP status values")
    void shouldHandleAllRsvpStatusValues() {
      // Given
      when(eventGuestRepo.findById(testEventGuestKey)).thenReturn(Optional.of(testEventGuest));

      // Test PENDING
      EventGuest pendingGuest =
          EventGuest.builder()
              .eventGuestKey(testEventGuestKey)
              .rsvpStatus(RsvpStatus.PENDING)
              .build();
      when(eventGuestRepo.save(any(EventGuest.class))).thenReturn(pendingGuest);

      RsvpStatus pendingResult = eventGuestService.setStatus(testEventGuestKey, RsvpStatus.PENDING);
      assertThat(pendingResult).isEqualTo(RsvpStatus.PENDING);

      // Test ACCEPTED
      EventGuest acceptedGuest =
          EventGuest.builder()
              .eventGuestKey(testEventGuestKey)
              .rsvpStatus(RsvpStatus.ACCEPTED)
              .build();
      when(eventGuestRepo.save(any(EventGuest.class))).thenReturn(acceptedGuest);

      RsvpStatus acceptedResult =
          eventGuestService.setStatus(testEventGuestKey, RsvpStatus.ACCEPTED);
      assertThat(acceptedResult).isEqualTo(RsvpStatus.ACCEPTED);

      // Test DECLINED
      EventGuest declinedGuest =
          EventGuest.builder()
              .eventGuestKey(testEventGuestKey)
              .rsvpStatus(RsvpStatus.DECLINED)
              .build();
      when(eventGuestRepo.save(any(EventGuest.class))).thenReturn(declinedGuest);

      RsvpStatus declinedResult =
          eventGuestService.setStatus(testEventGuestKey, RsvpStatus.DECLINED);
      assertThat(declinedResult).isEqualTo(RsvpStatus.DECLINED);
    }
  }

  @Nested
  @DisplayName("Integration Tests")
  class IntegrationTests {

    @Test
    @DisplayName(
        "Should handle complete workflow: create event with guests, add guest, update status, remove guest")
    void shouldHandleCompleteWorkflow() {
      // Given - Setup for createEventWithGuests
      Long organizerId = 1L;
      when(userService.getUserById(organizerId)).thenReturn(testOrganizer);
      when(userService.getAllUsersFromEmails(testEmails)).thenReturn(testUsers);
      when(eventGuestRepo.save(any(EventGuest.class))).thenReturn(testEventGuest);
      when(eventService.createEvent(any(EventModel.class))).thenReturn(testEvent);

      // When - Create event with guests
      EventModel createdEvent =
          eventGuestService.createEventWithGuests(organizerId, testEvent, testEmails);

      // Then - Verify event creation
      assertThat(createdEvent).isNotNull();
      verify(eventService).createEvent(testEvent);

      // Given - Setup for addNewGuestToEvent
      String newGuestEmail = "newguest@example.com";
      User newGuest = new User();
      newGuest.setId(4L);
      newGuest.setEmail(newGuestEmail);

      when(eventService.getEventById(anyLong())).thenReturn(testEvent);
      when(userService.getUserByEmail(newGuestEmail)).thenReturn(newGuest);

      // When - Add new guest
      EventGuest addedGuest = eventGuestService.addNewGuestToEvent(1L, newGuestEmail);

      // Then - Verify guest addition
      assertThat(addedGuest).isNotNull();

      // Given - Setup for setStatus
      when(eventGuestRepo.findById(any(EventGuestKey.class)))
          .thenReturn(Optional.of(testEventGuest));
      EventGuest acceptedGuest =
          EventGuest.builder()
              .eventGuestKey(testEventGuestKey)
              .rsvpStatus(RsvpStatus.ACCEPTED)
              .build();
      when(eventGuestRepo.save(any(EventGuest.class))).thenReturn(acceptedGuest);

      // When - Update status
      RsvpStatus updatedStatus =
          eventGuestService.setStatus(testEventGuestKey, RsvpStatus.ACCEPTED);

      // Then - Verify status update
      assertThat(updatedStatus).isEqualTo(RsvpStatus.ACCEPTED);

      // Given - Setup for removeGuestFromEvent
      when(userService.getUserByEmail(newGuestEmail)).thenReturn(newGuest);
      when(eventGuestRepo.existsById(any(EventGuestKey.class))).thenReturn(true);

      // When - Remove guest
      Boolean removed = eventGuestService.removeGuestFromEvent(newGuestEmail, 1L);

      // Then - Verify guest removal
      assertThat(removed).isTrue();
      verify(eventGuestRepo).deleteById(any(EventGuestKey.class));
    }
  }
}
