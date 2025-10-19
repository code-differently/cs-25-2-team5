package com.api.demo.models;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import com.api.demo.models.User;
import com.api.demo.models.EventModel;
import com.api.demo.models.EventGuest;

public class EventGuestTest {

    @Test
    @DisplayName("Test EventGuest creation with User and EventModel")
    public void testEventGuestCreation() {
    User user = new User("Test User", "test@example.com", "password123");
    EventModel event = new EventModel();
    event.setTitle("Test Event");

        EventGuest eventGuest = new EventGuest();
        eventGuest.setUser(user);
        eventGuest.setEvent(event);

        assertNotNull(eventGuest);
        assertEquals(user, eventGuest.getUser());
        assertEquals(event, eventGuest.getEvent());
    }

    @Test
    @DisplayName("Test EventGuest default constructor and setters")
    public void testEventGuestSetters() {
        EventGuest eventGuest = new EventGuest();
        assertNull(eventGuest.getUser());
        assertNull(eventGuest.getEvent());

    User user = new User("Guest", "guest@example.com", "guestpass");
    EventModel event = new EventModel();
    event.setTitle("Guest Event");

        eventGuest.setUser(user);
        eventGuest.setEvent(event);

        assertEquals(user, eventGuest.getUser());
        assertEquals(event, eventGuest.getEvent());
    }

    @Test
    @DisplayName("Test EventGuest id field")
    public void testEventGuestId() {
        EventGuest eventGuest = new EventGuest();
        eventGuest.setId(42L);
        assertEquals(42L, eventGuest.getId());
    }
}
