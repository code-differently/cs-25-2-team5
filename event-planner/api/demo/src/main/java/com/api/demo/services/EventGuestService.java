package com.api.demo.services;

import com.api.demo.models.EventGuest;
import com.api.demo.models.EventGuestKey;
import com.api.demo.models.EventModel;
import com.api.demo.models.RsvpStatus;
import com.api.demo.models.User;
import com.api.demo.repos.EventGuestRepo;
import jakarta.transaction.Transactional;
import java.util.HashSet;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EventGuestService {

  private final EventGuestRepo eventGuestRepo;
  private final UserService userService;
  private final EventService eventService;

  @Autowired
  public EventGuestService(
      EventGuestRepo eventGuestRepo, UserService userService, EventService eventService) {
    this.eventGuestRepo = eventGuestRepo;
    this.userService = userService;
    this.eventService = eventService;
  }

  // create a new event guest set the rsvp status by default
  @Transactional
  public EventModel createEventWithGuests(Long userId, EventModel event, Set<String> emails) {
    User organizer = userService.getUserById(userId);
    Set<User> usersFromEmails = userService.getAllUsersFromEmails(emails);
    event.setIsPublic(false);

    Set<EventGuest> eventGuests = new HashSet<>();

    for (User user : usersFromEmails) {
      EventGuest newGuest =
          EventGuest.builder()
              .eventGuestKey(new EventGuestKey(event.getId(), (long) user.getId()))
              .rsvpStatus(RsvpStatus.PENDING)
              .event(event)
              .guest(user)
              .build();
      eventGuestRepo.save(newGuest);
      eventGuests.add(newGuest);
    }

    event.setOrganizer(organizer);
    event.setEventGuests(eventGuests);
    return eventService.createEvent(event);
  }

  /*
   *
   * Get the Event by the Id
   * Get the Organizer
   * Create a new Event Guest using those Ids and save it to the database and update the event
   * Will be a post mapping in controller
   */
  public EventGuest addNewGuestToEvent(Long eventId, String guestEmail) {
    // Get the event
    EventModel event = eventService.getEventById(eventId);

    // Get the guest user by email
    User guest = userService.getUserByEmail(guestEmail);

    // Create EventGuestKey
    EventGuestKey key = new EventGuestKey(eventId, (long) guest.getId());

    // Create new EventGuest
    EventGuest newGuest =
        EventGuest.builder()
            .eventGuestKey(key)
            .rsvpStatus(RsvpStatus.PENDING)
            .event(event)
            .guest(guest)
            .build();

    return eventGuestRepo.save(newGuest);
  }

  public Boolean removeGuestFromEvent(String email, Long eventId) {
    // Get user by email
    User user = userService.getUserByEmail(email);

    // Create the composite key
    EventGuestKey key = new EventGuestKey(eventId, (long) user.getId());

    // Check if the event guest exists
    if (eventGuestRepo.existsById(key)) {
      eventGuestRepo.deleteById(key);
      return true;
    }
    return false;
  }

  /** Update RSVP status for an event guest */
  @Transactional
  public RsvpStatus setStatus(EventGuestKey guestKey, RsvpStatus status) {
    return eventGuestRepo
        .findById(guestKey)
        .map(
            eventGuest -> {
              eventGuest.setRsvpStatus(status);
              EventGuest saved = eventGuestRepo.save(eventGuest);
              return saved.getRsvpStatus();
            })
        .orElseThrow(() -> new EventGuestNotFoundException(guestKey));
  }
}
