package com.api.demo.services;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import com.api.demo.models.EventModel;
import com.api.demo.repos.EventModelRepo;
@SpringBootTest
@ActiveProfiles("test")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class EventServiceTest {

  @Autowired
  private EventModelRepo eventModelRepo;

  @Autowired
  private EventService eventService;

  @BeforeEach
  public void setUp() {
    System.out.println("Setting up test data...");
    eventModelRepo.deleteAll();
    EventModel event1 = new EventModel();
    event1.setTitle("Public Event 1");
    event1.setIsPublic(true);
    eventModelRepo.save(event1);

    EventModel event2 = new EventModel();
    event2.setTitle("Public Event 2");
    event2.setIsPublic(true);
    eventModelRepo.save(event2);
  }

  @Test
  public void testGetAllPublicEvents() {
    // Act
    Iterable<EventModel> result = eventService.getAllPublicEvents();

    // Assert
    Assertions.assertNotNull(result);
    List<EventModel> resultList = new ArrayList<>();
    result.forEach(resultList::add);
    Assertions.assertEquals(2, resultList.size());
    Assertions.assertTrue(resultList.stream().allMatch(EventModel::getIsPublic));
  }
}
