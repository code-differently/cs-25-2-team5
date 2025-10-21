package com.api.demo.services;

import com.api.demo.models.EventGuest;
import com.api.demo.models.EventGuestKey;
import com.api.demo.repos.EventGuestRepo;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;
import org.springframework.stereotype.Service;

@Service
public class EventGuestService {

  private final EventGuestRepo eventGuestRepo;

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
   */
  public EventGuest saveEventGuest(EventGuest eventGuest) {
    return eventGuestRepo.save(eventGuest);
  }

  /**
   * Deletes an EventGuest by composite key.
   *
   * @param eventId the event ID
   * @param guestId the guest ID
   */
  public void deleteEventGuest(Long eventId, Long guestId) {
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
