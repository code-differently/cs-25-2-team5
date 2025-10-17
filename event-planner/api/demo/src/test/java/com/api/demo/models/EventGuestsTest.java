package com.api.demo.models;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Nested;
import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDateTime;

@DisplayName("EventGuest Model Tests")
public class EventGuestsTest {

    private EventGuest eventGuest;
    private EventGuestKey eventGuestKey;
    private EventModel event;
    private User guest;

    @BeforeEach
    void setUp() {
        // Create test data
        eventGuestKey = new EventGuestKey(1L, 1L);
        
        event = new EventModel();
        event.setId(1L);
        event.setTitle("Test Event");
        event.setDescription("Test Description");
        event.setIsPublic(true);
        event.setStartTime(LocalDateTime.now().plusDays(1));
        
        guest = new User();
        guest.setId(1);
        guest.setName("John Doe");
        guest.setEmail("john@example.com");
        guest.setPassword("password123");
        
        eventGuest = new EventGuest();
    }

    @Nested
    @DisplayName("Constructor Tests")
    class ConstructorTests {
        
        @Test
        @DisplayName("Should create EventGuest with no-args constructor")
        void shouldCreateEventGuestWithNoArgsConstructor() {
            EventGuest newEventGuest = new EventGuest();
            assertThat(newEventGuest).isNotNull();
            assertThat(newEventGuest.getEventGuestKey()).isNull();
            assertThat(newEventGuest.getEvent()).isNull();
            assertThat(newEventGuest.getGuest()).isNull();
            assertThat(newEventGuest.getRsvpStatus()).isNull();
        }
    }

    @Nested
    @DisplayName("Getter and Setter Tests")
    class GetterSetterTests {
        
        @Test
        @DisplayName("Should set and get EventGuestKey")
        void shouldSetAndGetEventGuestKey() {
            eventGuest.setEventGuestKey(eventGuestKey);
            assertThat(eventGuest.getEventGuestKey()).isEqualTo(eventGuestKey);
            assertThat(eventGuest.getEventGuestKey().getEventId()).isEqualTo(1L);
            assertThat(eventGuest.getEventGuestKey().getGuestId()).isEqualTo(1L);
        }

        @Test
        @DisplayName("Should set and get Event")
        void shouldSetAndGetEvent() {
            eventGuest.setEvent(event);
            assertThat(eventGuest.getEvent()).isEqualTo(event);
            assertThat(eventGuest.getEvent().getTitle()).isEqualTo("Test Event");
        }

        @Test
        @DisplayName("Should set and get Guest")
        void shouldSetAndGetGuest() {
            eventGuest.setGuest(guest);
            assertThat(eventGuest.getGuest()).isEqualTo(guest);
            assertThat(eventGuest.getGuest().getName()).isEqualTo("John Doe");
        }

        @Test
        @DisplayName("Should set and get RSVP Status")
        void shouldSetAndGetRsvpStatus() {
            eventGuest.setRsvpStatus(RsvpStatus.ACCEPTED);
            assertThat(eventGuest.getRsvpStatus()).isEqualTo(RsvpStatus.ACCEPTED);
        }

        @Test
        @DisplayName("Should handle null values gracefully")
        void shouldHandleNullValues() {
            eventGuest.setEventGuestKey(null);
            eventGuest.setEvent(null);
            eventGuest.setGuest(null);
            eventGuest.setRsvpStatus(null);

            assertThat(eventGuest.getEventGuestKey()).isNull();
            assertThat(eventGuest.getEvent()).isNull();
            assertThat(eventGuest.getGuest()).isNull();
            assertThat(eventGuest.getRsvpStatus()).isNull();
        }
    }

    @Nested
    @DisplayName("RSVP Status Tests")
    class RsvpStatusTests {
        
        @Test
        @DisplayName("Should handle PENDING status")
        void shouldHandlePendingStatus() {
            eventGuest.setRsvpStatus(RsvpStatus.PENDING);
            assertThat(eventGuest.getRsvpStatus()).isEqualTo(RsvpStatus.PENDING);
        }

        @Test
        @DisplayName("Should handle ACCEPTED status")
        void shouldHandleAcceptedStatus() {
            eventGuest.setRsvpStatus(RsvpStatus.ACCEPTED);
            assertThat(eventGuest.getRsvpStatus()).isEqualTo(RsvpStatus.ACCEPTED);
        }

        @Test
        @DisplayName("Should handle DECLINED status")
        void shouldHandleDeclinedStatus() {
            eventGuest.setRsvpStatus(RsvpStatus.DECLINED);
            assertThat(eventGuest.getRsvpStatus()).isEqualTo(RsvpStatus.DECLINED);
        }
    }

    @Nested
    @DisplayName("Relationship Tests")
    class RelationshipTests {
        
        @Test
        @DisplayName("Should maintain proper relationship with Event")
        void shouldMaintainProperRelationshipWithEvent() {
            eventGuest.setEvent(event);
            eventGuest.setEventGuestKey(eventGuestKey);
            
            assertThat(eventGuest.getEvent()).isNotNull();
            assertThat(eventGuest.getEvent().getId()).isEqualTo(1L);
            assertThat(eventGuest.getEventGuestKey().getEventId()).isEqualTo(event.getId());
        }

        @Test
        @DisplayName("Should maintain proper relationship with Guest")
        void shouldMaintainProperRelationshipWithGuest() {
            eventGuest.setGuest(guest);
            eventGuest.setEventGuestKey(eventGuestKey);
            
            assertThat(eventGuest.getGuest()).isNotNull();
            assertThat(eventGuest.getGuest().getId()).isEqualTo(1);
            assertThat(eventGuest.getEventGuestKey().getGuestId()).isEqualTo(1L);
        }

        @Test
        @DisplayName("Should handle complete EventGuest setup")
        void shouldHandleCompleteEventGuestSetup() {
            eventGuest.setEventGuestKey(eventGuestKey);
            eventGuest.setEvent(event);
            eventGuest.setGuest(guest);
            eventGuest.setRsvpStatus(RsvpStatus.ACCEPTED);

            assertThat(eventGuest.getEventGuestKey()).isEqualTo(eventGuestKey);
            assertThat(eventGuest.getEvent()).isEqualTo(event);
            assertThat(eventGuest.getGuest()).isEqualTo(guest);
            assertThat(eventGuest.getRsvpStatus()).isEqualTo(RsvpStatus.ACCEPTED);
        }
    }

    @Nested
    @DisplayName("Equals and HashCode Tests")
    class EqualsAndHashCodeTests {
        
        @Test
        @DisplayName("Should be equal when all fields are the same")
        void shouldBeEqualWhenAllFieldsAreTheSame() {
            EventGuest eventGuest1 = new EventGuest();
            EventGuest eventGuest2 = new EventGuest();
            
            EventGuestKey key = new EventGuestKey(1L, 1L);
            
            eventGuest1.setEventGuestKey(key);
            eventGuest1.setEvent(event);
            eventGuest1.setGuest(guest);
            eventGuest1.setRsvpStatus(RsvpStatus.ACCEPTED);

            eventGuest2.setEventGuestKey(key);
            eventGuest2.setEvent(event);
            eventGuest2.setGuest(guest);
            eventGuest2.setRsvpStatus(RsvpStatus.ACCEPTED);

            assertThat(eventGuest1).isEqualTo(eventGuest2);
            assertThat(eventGuest1.hashCode()).isEqualTo(eventGuest2.hashCode());
        }

        @Test
        @DisplayName("Should not be equal when fields differ")
        void shouldNotBeEqualWhenFieldsDiffer() {
            EventGuest eventGuest1 = new EventGuest();
            EventGuest eventGuest2 = new EventGuest();
            
            eventGuest1.setEventGuestKey(new EventGuestKey(1L, 1L));
            eventGuest1.setRsvpStatus(RsvpStatus.ACCEPTED);

            eventGuest2.setEventGuestKey(new EventGuestKey(2L, 2L));
            eventGuest2.setRsvpStatus(RsvpStatus.DECLINED);

            assertThat(eventGuest1).isNotEqualTo(eventGuest2);
        }

        @Test
        @DisplayName("Should handle null comparison")
        void shouldHandleNullComparison() {
            assertThat(eventGuest).isNotEqualTo(null);
        }

        @Test
        @DisplayName("Should be equal to itself")
        void shouldBeEqualToItself() {
            assertThat(eventGuest).isEqualTo(eventGuest);
        }
    }

    @Nested
    @DisplayName("ToString Tests")
    class ToStringTests {
        
        @Test
        @DisplayName("Should generate proper toString representation")
        void shouldGenerateProperToStringRepresentation() {
            eventGuest.setEventGuestKey(eventGuestKey);
            eventGuest.setEvent(event);
            eventGuest.setGuest(guest);
            eventGuest.setRsvpStatus(RsvpStatus.ACCEPTED);

            String toString = eventGuest.toString();
            
            assertThat(toString).contains("EventGuest");
            assertThat(toString).contains("eventGuestKey");
            assertThat(toString).contains("rsvpStatus");
        }

        @Test
        @DisplayName("Should handle toString with null values")
        void shouldHandleToStringWithNullValues() {
            String toString = eventGuest.toString();
            assertThat(toString).isNotNull();
            assertThat(toString).contains("EventGuest");
        }
    }

    @Nested
    @DisplayName("Edge Case Tests")
    class EdgeCaseTests {
        
        @Test
        @DisplayName("Should handle EventGuestKey with different IDs")
        void shouldHandleEventGuestKeyWithDifferentIds() {
            EventGuestKey key1 = new EventGuestKey(1L, 2L);
            EventGuestKey key2 = new EventGuestKey(3L, 4L);
            
            eventGuest.setEventGuestKey(key1);
            assertThat(eventGuest.getEventGuestKey().getEventId()).isEqualTo(1L);
            assertThat(eventGuest.getEventGuestKey().getGuestId()).isEqualTo(2L);
            
            eventGuest.setEventGuestKey(key2);
            assertThat(eventGuest.getEventGuestKey().getEventId()).isEqualTo(3L);
            assertThat(eventGuest.getEventGuestKey().getGuestId()).isEqualTo(4L);
        }

        @Test
        @DisplayName("Should handle changing RSVP status")
        void shouldHandleChangingRsvpStatus() {
            eventGuest.setRsvpStatus(RsvpStatus.PENDING);
            assertThat(eventGuest.getRsvpStatus()).isEqualTo(RsvpStatus.PENDING);
            
            eventGuest.setRsvpStatus(RsvpStatus.ACCEPTED);
            assertThat(eventGuest.getRsvpStatus()).isEqualTo(RsvpStatus.ACCEPTED);
            
            eventGuest.setRsvpStatus(RsvpStatus.DECLINED);
            assertThat(eventGuest.getRsvpStatus()).isEqualTo(RsvpStatus.DECLINED);
        }
    }
}
