package com.api.demo.services;

import com.api.demo.exceptions.EventGuestNotFoundException;
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
  @Autowired
  public EventGuestService(EventGuestRepo eventGuestRepo) {
    this.eventGuestRepo = eventGuestRepo;
  }

  /**
   * Retrieves all guests for a specific event.
   *
   * @param eventId the ID of the event
   * @return list of EventGuest entities for the specified event
   */
  public List<EventGuest> getGuestsByEventId(Long eventId) {
    return StreamSupport.stream(eventGuestRepo.findAllByEventGuestKeyEventId(eventId).spliterator(), false)
        .collect(Collectors.toList());
  }

  /**
   * Retrieves all events for a specific guest.
   *
   * @param guestId the ID of the guest
   * @return list of EventGuest entities for the specified guest
   */
  public List<EventGuest> getEventsByGuestId(Long guestId) {
    return StreamSupport.stream(eventGuestRepo.findAllByEventGuestKeyGuestId(guestId).spliterator(), false)
        .collect(Collectors.toList());
  }

  /**
   * Retrieves a specific EventGuest by composite key.
   *
   * @param eventId the event ID
   * @param guestId the guest ID
   * @return Optional containing the EventGuest if found
   */
  public Optional<EventGuest> getEventGuest(Long eventId, Long guestId) {
    EventGuestKey key = new EventGuestKey(eventId, guestId);
    return eventGuestRepo.findById(key);
  }

  /**
   * Saves or updates an EventGuest.
   *
   * @param eventGuest the EventGuest to save
   * @return the saved EventGuest
   * @throws IllegalArgumentException if eventGuest is null
   */
  public EventGuest saveEventGuest(EventGuest eventGuest) {
    if (eventGuest == null) {
      throw new IllegalArgumentException("EventGuest cannot be null");
    }
    return eventGuestRepo.save(eventGuest);
  }

  /**
   * Deletes an EventGuest by composite key.
   *
   * @param eventId the event ID
   * @param guestId the guest ID
   * @throws IllegalArgumentException if eventId or guestId is null
   */
  public void deleteEventGuest(Long eventId, Long guestId) {
    if (eventId == null || guestId == null) {
      throw new IllegalArgumentException("EventId and GuestId cannot be null");
    }
    EventGuestKey key = new EventGuestKey(eventId, guestId);
    eventGuestRepo.deleteById(key);
  }

  /**
   * Checks if an EventGuest exists.
   *
   * @param eventId the event ID
   * @param guestId the guest ID
   * @return true if the EventGuest exists, false otherwise
   */
  public boolean existsEventGuest(Long eventId, Long guestId) {
    EventGuestKey key = new EventGuestKey(eventId, guestId);
    return eventGuestRepo.existsById(key);
  }
}
