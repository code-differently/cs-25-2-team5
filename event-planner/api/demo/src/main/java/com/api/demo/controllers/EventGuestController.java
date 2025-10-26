package com.api.demo.controllers;

import com.api.demo.dtos.CreateEventWithGuestsRequest;
import com.api.demo.dtos.DTOConverter;
import com.api.demo.models.EventGuest;
import com.api.demo.models.EventGuestKey;
import com.api.demo.models.EventModel;
import com.api.demo.models.RsvpStatus;
import com.api.demo.services.EventGuestService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/v1/event-guests")
public class EventGuestController {

  private final EventGuestService eventGuestService;

  @Autowired
  public EventGuestController(EventGuestService eventGuestService) {
    this.eventGuestService = eventGuestService;
  }

  // Get all guests for a specific event
  @GetMapping("/event/{eventId}")
  public ResponseEntity<List<EventGuest>> getGuestsByEventId(@PathVariable Long eventId) {
    List<EventGuest> guests = eventGuestService.getGuestsByEventId(eventId);
    return ResponseEntity.ok(guests);
  }

  // Get all events for a specific guest
  @GetMapping("/guest/{guestId}")
  public ResponseEntity<List<EventGuest>> getEventsByGuestId(@PathVariable Long guestId) {
    List<EventGuest> events = eventGuestService.getEventsByGuestId(guestId);
    return ResponseEntity.ok(events);
  }

  // Get a specific event-guest relationship
  @GetMapping("/event/{eventId}/guest/{guestId}")
  public ResponseEntity<EventGuest> getEventGuest(
      @PathVariable Long eventId, @PathVariable Long guestId) {
    EventGuest eventGuest = eventGuestService.getEventGuest(eventId, guestId);
    return ResponseEntity.ok(eventGuest);
  }

  // Create event with guests (from EventGuestService)
  @PostMapping("/organizer/{organizerId}/event/create")
  public ResponseEntity<EventModel> createEventWithGuests(
      @PathVariable Long organizerId, @RequestBody CreateEventWithGuestsRequest request) {
    EventModel createdEvent = DTOConverter.mapToModel(request);
    EventModel savedEvent = eventGuestService.createEventWithGuests(organizerId, createdEvent, request.getGuestEmails());

    return ResponseEntity.status(HttpStatus.CREATED).body(savedEvent);
  }

  // Add a new guest to an existing event
  @PostMapping("/event/{eventId}/guest")
  public ResponseEntity<EventGuest> addGuestToEvent(
      @PathVariable Long eventId, @RequestParam String guestEmail) {
    EventGuest newGuest = eventGuestService.addNewGuestToEvent(eventId, guestEmail);
    return ResponseEntity.status(HttpStatus.CREATED).body(newGuest);
  }

  // Remove a guest from an event
  @DeleteMapping("/event/{eventId}/guest")
  public ResponseEntity<Void> removeGuestFromEvent(
      @PathVariable Long eventId, @RequestParam String guestEmail) {
    Boolean removed = eventGuestService.removeGuestFromEvent(guestEmail, eventId);
    return removed ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
  }

  // Update RSVP status
  @PutMapping("/rsvp")
  public ResponseEntity<RsvpStatus> updateRsvpStatus(
      @RequestParam Long eventId, @RequestParam Long guestId, @RequestBody RsvpStatus status) {
    EventGuestKey key = new EventGuestKey(eventId, guestId);
    RsvpStatus updatedStatus = eventGuestService.setStatus(key, status);
    return ResponseEntity.ok(updatedStatus);
  }

  // Save or update an event guest
  @PostMapping
  public ResponseEntity<EventGuest> saveEventGuest(@RequestBody EventGuest eventGuest) {
    EventGuest savedGuest = eventGuestService.saveEventGuest(eventGuest);
    return ResponseEntity.status(HttpStatus.CREATED).body(savedGuest);
  }

  // Check if event-guest relationship exists
  @GetMapping("/event/{eventId}/guest/{guestId}/exists")
  public ResponseEntity<Boolean> checkEventGuestExists(
      @PathVariable Long eventId, @PathVariable Long guestId) {
    boolean exists = eventGuestService.existsEventGuest(eventId, guestId);
    return ResponseEntity.ok(exists);
  }

  // Delete event-guest relationship
  @DeleteMapping("/event/{eventId}/guest/{guestId}")
  public ResponseEntity<Void> deleteEventGuest(
      @PathVariable Long eventId, @PathVariable Long guestId) {
    eventGuestService.deleteEventGuest(eventId, guestId);
    return ResponseEntity.noContent().build();
  }
}
