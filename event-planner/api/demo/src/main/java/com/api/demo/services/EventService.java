package com.api.demo.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.api.demo.models.EventModel;
import com.api.demo.repos.EventModelRepo;

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

  public EventModel getEventById(int id) {
    return eventRepository.findById(id);
  }
}
