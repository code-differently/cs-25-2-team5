package com.api.demo.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.api.demo.exceptions.UserNotFoundException;
import com.api.demo.models.EventModel;
import com.api.demo.models.User;
import com.api.demo.repos.UserRepository;

@Service
public class UserService {
    private UserRepository userRepository;
    private EventService eventService;

    @Autowired
    public UserService(UserRepository userRepository, EventService eventService) {
        this.userRepository = userRepository;
        this.eventService = eventService;
    }
    public User createUser(User user) {
        return userRepository.save(user);
    }

    public User getUserById(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new UserNotFoundException("User not found"));
    }

    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email).orElseThrow(() -> new UserNotFoundException("User not found"));
    }
    @Transactional
    public EventModel createEvent(EventModel event,Long userId) {
        User user = getUserById(userId);
        user.getOrganizedEvents().add(event);
        event.setOrganizer(user);
        return eventService.createEvent(event);
    }

    

     

    
}

