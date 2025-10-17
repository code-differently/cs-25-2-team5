package com.api.demo;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import org.junit.jupiter.api.Test;

import com.api.demo.models.EventModel;

public class EventModelTests {

    private static final Long TEST_ID = 1L;
    private static final String TEST_TITLE = "Test Event";
    private static final String TEST_DESCRIPTION = "This is a test event";
    private static final LocalDateTime TEST_START_TIME = LocalDateTime.of(2025, 10, 16, 14, 30);

    public Long getTEST_ID() {
        return TEST_ID;
    }

    public String getTEST_TITLE() {
        return TEST_TITLE;
    }

    public String getTEST_DESCRIPTION() {
        return TEST_DESCRIPTION;
    }

    public LocalDateTime getTEST_START_TIME() {
        return TEST_START_TIME;
    }

    @Test
    void testEventModel_SettersAndGetters() {
        EventModel event = new EventModel();

        event.setId(getTEST_ID());
        event.setTitle(getTEST_TITLE());
        event.setDescription(getTEST_DESCRIPTION());
        event.setStartTime(getTEST_START_TIME());

        assertEquals(getTEST_ID(), event.getId());
        assertEquals(getTEST_TITLE(), event.getTitle());
        assertEquals(getTEST_DESCRIPTION(), event.getDescription());
        assertEquals(getTEST_START_TIME(), event.getStartTime());
    }

    @Test
    void testEventModel_DefaultConstructor() {
        EventModel event = new EventModel();
        
        assertNull(event.getId());
        assertNull(event.getTitle());
        assertNull(event.getDescription());
        assertNull(event.getStartTime());
    }
}