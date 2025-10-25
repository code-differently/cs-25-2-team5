package com.api.demo.controllers;

import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.api.demo.dtos.EventDTO;
import com.api.demo.models.EventModel;
import com.api.demo.models.User;
import com.api.demo.services.EventService;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

// This annotation tells JUnit to use Mockito for this test class
@ExtendWith(MockitoExtension.class)
class EventControllerTest {

  // @Mock creates a fake version of EventService
  // This fake service won't actually connect to a database
  @Mock private EventService eventService;

  // @InjectMocks creates a real EventController
  // and automatically injects the mock EventService into it
  @InjectMocks private EventController eventController;

  // MockMvc is a tool that lets us test API endpoints
  // without starting the entire Spring application
  private MockMvc mockMvc;

  // This method runs before each test
  // It sets up MockMvc with our controller
  @BeforeEach
  void setUp() {
    mockMvc = MockMvcBuilders.standaloneSetup(eventController).build();
  }

  // TEST 1: Check that the endpoint returns events correctly
  @Test
  void getAllCommunityEvents_ShouldReturnListOfEvents() throws Exception {
    // ARRANGE - Set up test data
    // Create fake event objects for testing
    EventModel event1 = new EventModel();
    event1.setId(1L);
    event1.setTitle("Community Event 1");
    event1.setIsPublic(true); // Set isPublic to avoid NullPointerException
    event1.setDescription("Test description 1");

    // Create organizer for event1
    User organizer1 = new User();
    organizer1.setId(1L);
    organizer1.setName("John Doe");
    organizer1.setEmail("john@example.com");
    event1.setOrganizer(organizer1);

    EventModel event2 = new EventModel();
    event2.setId(2L);
    event2.setTitle("Community Event 2");
    event2.setIsPublic(true); // Set isPublic to avoid NullPointerException
    event2.setDescription("Test description 2");

    // Create organizer for event2
    User organizer2 = new User();
    organizer2.setId(2L);
    organizer2.setName("Jane Smith");
    organizer2.setEmail("jane@example.com");
    event2.setOrganizer(organizer2);

    List<EventModel> mockEvents = Arrays.asList(event1, event2);

    // Tell the mock service what to return when getAllPublicEvents() is called
    when(eventService.getCommunityEvents()).thenReturn(mockEvents);

    // ACT & ASSERT - Make the API call and check the response
    mockMvc
        .perform(get("/api/v1/events/community"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$", hasSize(2)))
        .andExpect(jsonPath("$[0].id", is(1)))
        .andExpect(jsonPath("$[0].title", is("Community Event 1")))
        .andExpect(jsonPath("$[1].id", is(2)))
        .andExpect(jsonPath("$[1].title", is("Community Event 2")));

    // Verify that the service method was called exactly once
    verify(eventService, times(1)).getCommunityEvents();
  }

  @Test
  void getAllCommunityEvents_ShouldReturnEmptyList_WhenNoEvents() throws Exception {
    // ARRANGE - Tell mock service to return empty list
    when(eventService.getCommunityEvents()).thenReturn(List.of());

    // ACT & ASSERT - Call endpoint and verify empty response
    mockMvc
        .perform(get("/api/v1/events/community"))
        .andExpect(status().isOk()) // Should still return 200 OK
        .andExpect(jsonPath("$", hasSize(0))); // But with 0 events

    // Verify the service was called
    verify(eventService, times(1)).getCommunityEvents();
  }

  // TEST 3: Directly test the controller method (without HTTP)
  @Test
  void getAllCommunityEvents_ShouldCallServiceMethod() {
    // ARRANGE - Create fake events with proper data
    EventModel event1 = new EventModel();
    event1.setId(1L);
    event1.setTitle("Test Event 1");
    event1.setIsPublic(true);
    event1.setDescription("Test description");

    User organizer1 = new User();
    organizer1.setId(1L);
    organizer1.setName("Test User");
    organizer1.setEmail("test@example.com");
    event1.setOrganizer(organizer1);

    EventModel event2 = new EventModel();
    event2.setId(2L);
    event2.setTitle("Test Event 2");
    event2.setIsPublic(false);
    event2.setDescription("Test description 2");

    User organizer2 = new User();
    organizer2.setId(2L);
    organizer2.setName("Test User 2");
    organizer2.setEmail("test2@example.com");
    event2.setOrganizer(organizer2);

    List<EventModel> mockEvents = Arrays.asList(event1, event2);
    when(eventService.getCommunityEvents()).thenReturn(mockEvents);

    // ACT - Call the controller method directly
    List<EventDTO> result = eventController.getAllCommunityEvents();

    // ASSERT - Check that service was called and result has correct size
    verify(eventService, times(1)).getCommunityEvents();
    assert result.size() == 2;
  }
}
