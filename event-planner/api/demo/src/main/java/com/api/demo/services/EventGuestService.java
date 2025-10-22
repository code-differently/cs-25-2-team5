package com.api.demo.services;


import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
            }
            event.setOrganizer(organizer);
            return eventService.createEvent(event);

    }


    


    
    


}
