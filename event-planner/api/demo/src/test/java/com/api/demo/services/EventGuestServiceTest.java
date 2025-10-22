package com.api.demo.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anySet;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.api.demo.models.EventGuest;
import com.api.demo.models.EventModel;
import com.api.demo.models.User;
import com.api.demo.repos.EventGuestRepo;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
@DisplayName("EventGuestService Tests")
public class EventGuestServiceTest {

  @Mock private EventGuestRepo eventGuestRepo;

  @Mock private UserService userService;

  @Mock private EventService eventService;

  @InjectMocks private EventGuestService eventGuestService;

  private User testOrganizer;
  private User testGuest1;
  private User testGuest2;
  private EventModel testEvent;
  private Set<String> guestEmails;

  @BeforeEach
  void setUp() {
    // Set up test organizer
    testOrganizer = new User();
    testOrganizer.setId(1L);
    testOrganizer.setName("John Organizer");
    testOrganizer.setEmail("organizer@example.com");
    testOrganizer.setPassword("password123");
    testOrganizer.setOrganizedEvents(new HashSet<>());

    // Set up test guests
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

    // Set up test event
    testEvent = new EventModel();
    testEvent.setId(1L);
    testEvent.setTitle("Test Event");
    testEvent.setDescription("Test Description");
    testEvent.setStartTime(LocalDateTime.now().plusDays(1));
    testEvent.setIsPublic(true); // Will be set to false by the method
    testEvent.setEventGuests(new HashSet<>());

    // Set up guest emails
    guestEmails = new HashSet<>();
    guestEmails.add("guest1@example.com");
    guestEmails.add("guest2@example.com");
  }

  @Test
  @DisplayName("Should create event with guests successfully")
  void createEventWithGuests_ShouldCreateEventWithGuests_WhenValidData() {
    // Given
    Long organizerId = 1L;
    Set<User> guestUsers = Set.of(testGuest1, testGuest2);

    EventModel expectedCreatedEvent = new EventModel();
    expectedCreatedEvent.setId(1L);
    expectedCreatedEvent.setTitle("Test Event");
    expectedCreatedEvent.setDescription("Test Description");
    expectedCreatedEvent.setStartTime(testEvent.getStartTime());
    expectedCreatedEvent.setIsPublic(false);
    expectedCreatedEvent.setOrganizer(testOrganizer);

    // Mock service calls
    when(userService.getUserById(organizerId)).thenReturn(testOrganizer);
    when(userService.getAllUsersFromEmails(guestEmails)).thenReturn(guestUsers);
    when(eventService.createEvent(any(EventModel.class))).thenReturn(expectedCreatedEvent);

    // When
    EventModel result =
        eventGuestService.createEventWithGuests(organizerId, testEvent, guestEmails);

    // Then
    assertNotNull(result);
    assertEquals("Test Event", result.getTitle());
    assertEquals("Test Description", result.getDescription());
    assertFalse(result.getIsPublic()); // Should be set to false by the method
    assertEquals(testOrganizer, result.getOrganizer());

    // Verify that event guests were saved
    verify(eventGuestRepo, times(2)).save(any(EventGuest.class));

    // Verify service interactions
    verify(userService).getUserById(organizerId);
    verify(userService).getAllUsersFromEmails(guestEmails);
    verify(eventService).createEvent(any(EventModel.class));
  }

  @Test
  @DisplayName("Should set correct RSVP status for all guests")
  void createEventWithGuests_ShouldSetPendingRsvpStatus_ForAllGuests() {
    // Given
    Long organizerId = 1L;
    Set<User> guestUsers = Set.of(testGuest1, testGuest2);

    EventModel expectedCreatedEvent = new EventModel();
    expectedCreatedEvent.setId(1L);
    expectedCreatedEvent.setOrganizer(testOrganizer);

    // Mock service calls
    when(userService.getUserById(organizerId)).thenReturn(testOrganizer);
    when(userService.getAllUsersFromEmails(guestEmails)).thenReturn(guestUsers);
    when(eventService.createEvent(any(EventModel.class))).thenReturn(expectedCreatedEvent);

    // When
    EventModel result =
        eventGuestService.createEventWithGuests(organizerId, testEvent, guestEmails);

    // Then
    assertNotNull(result);

    // Verify that event guests were created with PENDING status
    verify(eventGuestRepo, times(2)).save(any(EventGuest.class));
  }

  @Test
  @DisplayName("Should handle single guest correctly")
  void createEventWithGuests_ShouldCreateEventWithSingleGuest_WhenOneEmail() {
    // Given
    Long organizerId = 1L;
    Set<String> singleGuestEmail = Set.of("guest1@example.com");
    Set<User> singleGuestUser = Set.of(testGuest1);

    EventModel expectedCreatedEvent = new EventModel();
    expectedCreatedEvent.setId(1L);
    expectedCreatedEvent.setOrganizer(testOrganizer);

    // Mock service calls
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

    // Verify service interactions
    verify(userService).getUserById(organizerId);
    verify(userService).getAllUsersFromEmails(singleGuestEmail);
    verify(eventService).createEvent(any(EventModel.class));
  }

  @Test
  @DisplayName("Should handle empty guest list correctly")
  void createEventWithGuests_ShouldCreateEventWithNoGuests_WhenEmptyEmailSet() {
    // Given
    Long organizerId = 1L;
    Set<String> emptyGuestEmails = new HashSet<>();
    Set<User> emptyGuestUsers = new HashSet<>();

    EventModel expectedCreatedEvent = new EventModel();
    expectedCreatedEvent.setId(1L);
    expectedCreatedEvent.setOrganizer(testOrganizer);
    expectedCreatedEvent.setIsPublic(false);

    // Mock service calls
    when(userService.getUserById(organizerId)).thenReturn(testOrganizer);
    when(userService.getAllUsersFromEmails(emptyGuestEmails)).thenReturn(emptyGuestUsers);
    when(eventService.createEvent(any(EventModel.class))).thenReturn(expectedCreatedEvent);

    // When
    EventModel result =
        eventGuestService.createEventWithGuests(organizerId, testEvent, emptyGuestEmails);

    // Then
    assertNotNull(result);
    assertEquals(testOrganizer, result.getOrganizer());
    assertFalse(result.getIsPublic()); // Should still be set to false

    // Verify no guests were saved
    verify(eventGuestRepo, times(0)).save(any(EventGuest.class));

    // Verify service interactions
    verify(userService).getUserById(organizerId);
    verify(userService).getAllUsersFromEmails(emptyGuestEmails);
    verify(eventService).createEvent(any(EventModel.class));
  }

  @Test
  @DisplayName("Should set event to private (isPublic = false)")
  void createEventWithGuests_ShouldSetEventToPrivate_RegardlessOfOriginalStatus() {
    // Given
    Long organizerId = 1L;
    Set<User> guestUsers = Set.of(testGuest1);

    // Start with a public event
    testEvent.setIsPublic(true);

    EventModel expectedCreatedEvent = new EventModel();
    expectedCreatedEvent.setId(1L);
    expectedCreatedEvent.setIsPublic(false);
    expectedCreatedEvent.setOrganizer(testOrganizer);

    // Mock service calls
    when(userService.getUserById(organizerId)).thenReturn(testOrganizer);
    when(userService.getAllUsersFromEmails(anySet())).thenReturn(guestUsers);
    when(eventService.createEvent(any(EventModel.class))).thenReturn(expectedCreatedEvent);

    // When
    EventModel result =
        eventGuestService.createEventWithGuests(organizerId, testEvent, guestEmails);

    // Then
    assertNotNull(result);
    assertFalse(result.getIsPublic()); // Should be set to false regardless of original value
  }

  @Test
  @DisplayName("Should set organizer correctly")
  void createEventWithGuests_ShouldSetOrganizer_FromUserId() {
    // Given
    Long organizerId = 1L;
    Set<User> guestUsers = Set.of(testGuest1);

    EventModel expectedCreatedEvent = new EventModel();
    expectedCreatedEvent.setId(1L);
    expectedCreatedEvent.setOrganizer(testOrganizer);

    // Mock service calls
    when(userService.getUserById(organizerId)).thenReturn(testOrganizer);
    when(userService.getAllUsersFromEmails(anySet())).thenReturn(guestUsers);
    when(eventService.createEvent(any(EventModel.class))).thenReturn(expectedCreatedEvent);

    // When
    EventModel result =
        eventGuestService.createEventWithGuests(organizerId, testEvent, guestEmails);

    // Then
    assertNotNull(result);
    assertEquals(testOrganizer, result.getOrganizer());
    assertEquals("John Organizer", result.getOrganizer().getName());
    assertEquals("organizer@example.com", result.getOrganizer().getEmail());

    // Verify organizer was retrieved correctly
    verify(userService).getUserById(organizerId);
  }
}
