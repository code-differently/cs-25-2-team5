package com.api.demo.controllers;

import com.api.demo.dtos.DTOConverter;
import com.api.demo.dtos.EventDTO;
import com.api.demo.models.EventModel;
import com.api.demo.services.EventService;
import jakarta.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/events")
@CrossOrigin("*")
public class EventController {
  @Autowired private EventService eventService;

  public EventController(EventService eventService) {
    this.eventService = eventService;
  }

  @GetMapping("/community")
  public List<EventDTO> getAllCommunityEvents() {
    Iterable<EventModel> events = eventService.getCommunityEvents();

    return StreamSupport.stream(events.spliterator(), false)
        .map(
            event -> {
              return DTOConverter.mapToDTO(event);
            })
        .collect(Collectors.toList());
  }

  @GetMapping("/{id}")
  public ResponseEntity<EventDTO> getEventById(@Valid @PathVariable Long id) {
    EventModel event = eventService.getEventById(id);
    EventDTO eventDTO = DTOConverter.mapToDTO(event);
    return ResponseEntity.ok(eventDTO);
  }
}
