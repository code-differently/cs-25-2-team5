package com.api.demo.services;


import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.api.demo.dtos.UserDTO;
import com.api.demo.models.EventGuest;
import com.api.demo.models.EventGuestKey;
import com.api.demo.models.EventModel;
import com.api.demo.models.RsvpStatus;
import com.api.demo.models.User;
import com.api.demo.repos.EventGuestRepo;

import jakarta.transaction.Transactional;


@Service
public class EventGuestService {

    private final EventGuestRepo eventGuestRepo;
    private UserService userService;
    private EventService eventService;
    
    @Autowired
    public EventGuestService(EventGuestRepo eventGuestRepo,UserService userService,EventService eventService) {
        this.eventGuestRepo = eventGuestRepo;
        this.userService = userService;
        this.eventService= eventService;
    }
    // create a new event guest set the rsvp status by default 
    @Transactional
    public EventModel createEventWithGuests(Long userId,EventModel event,Set<String> emails) {
            User organizer = userService.getUserById(userId);
            Set<User> usersFromEmails = userService.getAllUsersFromEmails(emails);
            event.setIsPublic(false);

            Set<EventGuest> eventGuests = new HashSet<>();

            for(User user:usersFromEmails) {
                EventGuest newGuest = EventGuest.builder()
                .eventGuestKey(new EventGuestKey(user.getId(), event.getId()))
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
    public EventGuest addNewGuestToEvent(Long eventId) {
        return null;

    }

    /*
     * Get the user from their email
     * find and event guest based on that key
     * Delete them from the database
     */
    public Boolean removeGuestFromEventLong(String email,Long eventId) {
            return true;

    }

    public RsvpStatus setStatus(EventGuestKey guest,RsvpStatus status) {
        return null;
    }
 

    



    


    
    


}
