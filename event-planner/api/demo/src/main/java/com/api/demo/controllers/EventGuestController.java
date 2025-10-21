package com.api.demo.controllers;

import com.api.demo.models.EventGuest;
import com.api.demo.repos.EventGuestRepo;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/event-guests")
public class EventGuestController {

  private final EventGuestRepo eventGuestRepo;

  public EventGuestController(EventGuestRepo eventGuestRepo) {
    this.eventGuestRepo = eventGuestRepo;
  }

  @GetMapping("/event/{eventId}")
  public List<EventGuest> getGuestsByEventId(@PathVariable Long eventId) {
    return (List<EventGuest>) eventGuestRepo.findAllByEventGuestKeyEventId(eventId);
  }

  @GetMapping("/guest/{guestId}")
  public List<EventGuest> getEventsByGuestId(@PathVariable Long guestId) {
    return (List<EventGuest>) eventGuestRepo.findAllByEventGuestKeyGuestId(guestId);
  }
}
