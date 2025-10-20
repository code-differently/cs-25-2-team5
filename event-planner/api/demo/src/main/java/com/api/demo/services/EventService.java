package com.api.demo.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.api.demo.models.EventModel;
import com.api.demo.repos.EventModelRepo;
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

    public Iterable<EventModel> getCommunityEvents() {
        return eventModelRepo.findAllByIsPublicTrue();
    }

}
