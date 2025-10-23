package com.api.demo.controllers;

import com.api.demo.models.EventModel;
import com.api.demo.services.EventService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
    Iterable<EventModel> events = eventService.getCommunityEvents();
    return (List<EventModel>) events;
  }

  @PutMapping("/{id}")
  public ResponseEntity<EventModel> updateEvent(
      @PathVariable Long id, 
      @RequestBody EventModel updatedEventInfo) {
    EventModel updatedEvent = eventService.updateEvent(id, updatedEventInfo);
    return ResponseEntity.ok(updatedEvent);
  }
}
