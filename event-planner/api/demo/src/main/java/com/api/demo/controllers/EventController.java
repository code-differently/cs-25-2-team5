package com.api.demo.controllers;

import com.api.demo.models.EventModel;
import com.api.demo.services.EventService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/events")
public class EventController {

  @Autowired private EventService eventService;

  public EventController(EventService eventService) {
    this.eventService = eventService;
  }

  @GetMapping("/community")
  public List<EventModel> getAllCommunityEvents() {
    Iterable<EventModel> events = eventService.getAllPublicEvents();
    return (List<EventModel>) events;
  }
}
