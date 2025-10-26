package com.api.demo.controllers;

import com.api.demo.dtos.EventDTO;
import com.api.demo.dtos.UserDTO;
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
              // Handle null organizer case
              UserDTO organizerDTO = null;
              if (event.getOrganizer() != null) {
                organizerDTO =
                    UserDTO.builder()
                        .id(event.getOrganizer().getId())
                        .name(event.getOrganizer().getName())
                        .email(event.getOrganizer().getEmail())
                        .build();
              }

              return EventDTO.builder()
                  .title(event.getTitle())
                  .description(event.getDescription())
                  .startTime(event.getStartTime())
                  .id(event.getId())
                  .eventType(
                      event.getIsPublic() != null && event.getIsPublic() ? "Community" : "Private")
                  .organizer(organizerDTO)
                  .imageURL(event.getImageURL())
                  .build();
            })
        .collect(Collectors.toList());
  }

  @GetMapping("/{id}")
  public ResponseEntity<EventDTO> getEventById(@Valid @PathVariable Long id) {
    EventModel event = eventService.getEventById(id);

    // Handle null organizer case
    UserDTO organizerDTO = null;
    if (event.getOrganizer() != null) {
      organizerDTO =
          UserDTO.builder()
              .name(event.getOrganizer().getName())
              .email(event.getOrganizer().getEmail())
              .id(event.getOrganizer().getId())
              .build();
    }

    EventDTO eventDTO =
        EventDTO.builder()
            .title(event.getTitle())
            .description(event.getDescription())
            .startTime(event.getStartTime())
            .eventType(event.getIsPublic() != null && event.getIsPublic() ? "Community" : "Private")
            .organizer(organizerDTO)
            .id(event.getId())
            .imageURL(event.getImageURL())
            .build();
    return ResponseEntity.ok(eventDTO);
  }
}
