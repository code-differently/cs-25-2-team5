package com.api.demo.services;

import com.api.demo.dtos.UserInviteDTO;
import com.api.demo.exceptions.UserNotFoundException;
import com.api.demo.models.EventGuest;
import com.api.demo.models.EventModel;
import com.api.demo.models.User;
import com.api.demo.repos.UserRepository;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/*
 * Service class for managing User-related operations.
 */
@Service
public class UserService {
  private final UserRepository userRepository;
  private final EventService eventService;

  @Autowired
  public UserService(UserRepository userRepository, EventService eventService) {
    this.userRepository = userRepository;
    this.eventService = eventService;
  }

  public User createUser(User user) {
    return userRepository.save(user);
  }

  public User getUserById(Long id) {
    return userRepository
        .findById(id)
        .orElseThrow(() -> new UserNotFoundException("User not found"));
  }

  public User getUserByEmail(String email) {
    email = email.toLowerCase();
    return userRepository
        .findByEmail(email)
        .orElseThrow(() -> new UserNotFoundException("User not found"));
  }

  /*
   * Creates an event and associates it with the user identified by userId.
   * @param event   The event to be created.
   * @param userId  The ID of the user organizing the event.
   *
   * @return The created EventModel.
   *
   * Uses @Transactional to ensure database integrity during the operation.
   */
  @Transactional
  public EventModel createPublicEvent(EventModel event, Long userId) {
    User user = getUserById(userId);
    user.getOrganizedEvents().add(event);
    event.setOrganizer(user);
    event.setIsPublic(true);
    event.setEventGuests(new HashSet<EventGuest>());
    return eventService.createEvent(event);
  }

  public Set<User> getAllUsersFromEmails(Set<String> emails) {
    return userRepository.findAllByEmailIn(emails);
  }

  public List<UserInviteDTO> getAllInvitedEvents(Long userId) {
    return userRepository.findAllUserInvitedEvents(userId);
  }
}
