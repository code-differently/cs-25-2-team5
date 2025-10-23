package com.api.demo.services;

import com.api.demo.exceptions.EventNotFoundException;
import com.api.demo.models.EventModel;
import com.api.demo.repos.EventModelRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EventService {

  private EventModelRepo eventModelRepo;

  @Autowired
  public EventService(EventModelRepo eventModelRepo) {
    this.eventModelRepo = eventModelRepo;
  }

  public EventModel createEvent(EventModel event) {
    return eventModelRepo.save(event);
  }

  public EventModel getEventById(Long eventId) {
    return eventModelRepo
        .findById(eventId)
        .orElseThrow(() -> new RuntimeException("Event not found with id: " + eventId));
  }

  public Iterable<EventModel> getCommunityEvents() {
    return eventModelRepo.findAllByIsPublicTrue();
  }

  public EventModel getEventById(Long id) {
    return eventModelRepo.findById(id)
        .orElseThrow(() -> new EventNotFoundException("Event not found with id: " + id));
  }

  public EventModel updateEvent(Long id, EventModel updatedEventInfo) {
    EventModel event = eventModelRepo.findById(id)
        .orElseThrow(() -> new EventNotFoundException("Event not found"));
    
    // Update fields if they are provided
    if (updatedEventInfo.getTitle() != null) {
      event.setTitle(updatedEventInfo.getTitle());
    }
    if (updatedEventInfo.getDescription() != null) {
      event.setDescription(updatedEventInfo.getDescription());
    }
    if (updatedEventInfo.getStartTime() != null) {
      event.setStartTime(updatedEventInfo.getStartTime());
    }
    if (updatedEventInfo.getIsPublic() != null) {
      event.setIsPublic(updatedEventInfo.getIsPublic());
    }
    
    return eventModelRepo.save(event);
  }
}
