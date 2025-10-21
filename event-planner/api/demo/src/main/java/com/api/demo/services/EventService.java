package com.api.demo.services;

import com.api.demo.exceptions.EventNotFoundException;
import com.api.demo.models.EventModel;
import com.api.demo.repos.EventModelRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EventService {
  private final EventModelRepo eventRepository;

  @Autowired
  public EventService(EventModelRepo eventRepository) {
    this.eventRepository = eventRepository;
  }

  public Iterable<EventModel> getAllPublicEvents() {
    return eventRepository.findAllByIsPublicTrue();
  }

  public EventModel getEventById(Long id) {
    return eventRepository
        .findById(id)
        .orElseThrow(() -> new EventNotFoundException("Event not found with id: " + id));
  }
}
