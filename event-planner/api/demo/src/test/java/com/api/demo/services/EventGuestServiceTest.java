package com.api.demo.services;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anySet;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

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

import com.api.demo.exceptions.UserNotFoundException;
import com.api.demo.models.EventGuest;
import com.api.demo.models.EventGuestKey;
import com.api.demo.models.EventModel;
import com.api.demo.models.RsvpStatus;
import com.api.demo.models.User;
import com.api.demo.repos.EventGuestRepo;

@ExtendWith(MockitoExtension.class)
@DisplayName("EventGuestService Tests")
class EventGuestServiceTest {

    @Mock
    private EventGuestRepo eventGuestRepo;

    @Mock
    private UserService userService;

    @Mock
    private EventService eventService;

    @InjectMocks
    private EventGuestService eventGuestService;

    private User testUser;
    private User testOrganizer;
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

        // Create test user
        testUser = new User();
        testUser.setId(2L);
        testUser.setName("Jane Guest");
        testUser.setEmail("guest@example.com");
        testUser.setPassword("password123");

        // Create test event
        testEvent = new EventModel();
        testEvent.setId(1L);
        testEvent.setTitle("Test Event");
        testEvent.setDescription("Test Description");
        testEvent.setStartTime(LocalDateTime.now().plusDays(1));
        testEvent.setOrganizer(testOrganizer);

        // Create test EventGuestKey
        testEventGuestKey = new EventGuestKey(1L, 2L);

        // Create test EventGuest
        testEventGuest = EventGuest.builder()
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

        User testUser2 = new User();
        testUser2.setId(3L);
        testUser2.setName("Bob Guest");
        testUser2.setEmail("guest2@example.com");
        testUsers.add(testUser2);
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
            EventModel result = eventGuestService.createEventWithGuests(organizerId, testEvent, testEmails);

            // Then
            assertThat(result).isNotNull();
            assertThat(result.getIsPublic()).isFalse();
            assertThat(result.getOrganizer()).isEqualTo(testOrganizer);
            
            verify(userService).getUserById(organizerId);
            verify(userService).getAllUsersFromEmails(testEmails);
            verify(eventGuestRepo, times(2)).save(any(EventGuest.class)); // 2 users in testUsers
            verify(eventService).createEvent(testEvent);
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
            EventModel result = eventGuestService.createEventWithGuests(organizerId, testEvent, emptyEmails);

            // Then
            assertThat(result).isNotNull();
            verify(eventGuestRepo, never()).save(any(EventGuest.class));
            verify(eventService).createEvent(testEvent);
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
            
            when(userService.getUserByEmail(guestEmail)).thenThrow(new UserNotFoundException("User not found"));

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
    @DisplayName("setStatus Tests")
    class SetStatusTests {

        @Test
        @DisplayName("Should update RSVP status successfully")
        void shouldUpdateRsvpStatusSuccessfully() {
            // Given
            RsvpStatus newStatus = RsvpStatus.ACCEPTED;
            EventGuest updatedEventGuest = EventGuest.builder()
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
                .isInstanceOf(RuntimeException.class)
                .hasMessage("EventGuest not found");
            
            verify(eventGuestRepo).findById(testEventGuestKey);
            verify(eventGuestRepo, never()).save(any(EventGuest.class));
        }

        @Test
        @DisplayName("Should handle all RSVP status values")
        void shouldHandleAllRsvpStatusValues() {
            // Given
            when(eventGuestRepo.findById(testEventGuestKey)).thenReturn(Optional.of(testEventGuest));
            
            // Test PENDING
            EventGuest pendingGuest = EventGuest.builder()
                .eventGuestKey(testEventGuestKey)
                .rsvpStatus(RsvpStatus.PENDING)
                .build();
            when(eventGuestRepo.save(any(EventGuest.class))).thenReturn(pendingGuest);
            
            RsvpStatus pendingResult = eventGuestService.setStatus(testEventGuestKey, RsvpStatus.PENDING);
            assertThat(pendingResult).isEqualTo(RsvpStatus.PENDING);
            
            // Test ACCEPTED
            EventGuest acceptedGuest = EventGuest.builder()
                .eventGuestKey(testEventGuestKey)
                .rsvpStatus(RsvpStatus.ACCEPTED)
                .build();
            when(eventGuestRepo.save(any(EventGuest.class))).thenReturn(acceptedGuest);
            
            RsvpStatus acceptedResult = eventGuestService.setStatus(testEventGuestKey, RsvpStatus.ACCEPTED);
            assertThat(acceptedResult).isEqualTo(RsvpStatus.ACCEPTED);
            
            // Test DECLINED
            EventGuest declinedGuest = EventGuest.builder()
                .eventGuestKey(testEventGuestKey)
                .rsvpStatus(RsvpStatus.DECLINED)
                .build();
            when(eventGuestRepo.save(any(EventGuest.class))).thenReturn(declinedGuest);
            
            RsvpStatus declinedResult = eventGuestService.setStatus(testEventGuestKey, RsvpStatus.DECLINED);
            assertThat(declinedResult).isEqualTo(RsvpStatus.DECLINED);
        }
    }

    @Nested
    @DisplayName("Integration Tests")
    class IntegrationTests {

        @Test
        @DisplayName("Should handle complete workflow: create event with guests, add guest, update status, remove guest")
        void shouldHandleCompleteWorkflow() {
            // Given - Setup for createEventWithGuests
            Long organizerId = 1L;
            when(userService.getUserById(organizerId)).thenReturn(testOrganizer);
            when(userService.getAllUsersFromEmails(testEmails)).thenReturn(testUsers);
            when(eventGuestRepo.save(any(EventGuest.class))).thenReturn(testEventGuest);
            when(eventService.createEvent(any(EventModel.class))).thenReturn(testEvent);

            // When - Create event with guests
            EventModel createdEvent = eventGuestService.createEventWithGuests(organizerId, testEvent, testEmails);
            
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
            when(eventGuestRepo.findById(any(EventGuestKey.class))).thenReturn(Optional.of(testEventGuest));
            EventGuest acceptedGuest = EventGuest.builder()
                .eventGuestKey(testEventGuestKey)
                .rsvpStatus(RsvpStatus.ACCEPTED)
                .build();
            when(eventGuestRepo.save(any(EventGuest.class))).thenReturn(acceptedGuest);

            // When - Update status
            RsvpStatus updatedStatus = eventGuestService.setStatus(testEventGuestKey, RsvpStatus.ACCEPTED);
            
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