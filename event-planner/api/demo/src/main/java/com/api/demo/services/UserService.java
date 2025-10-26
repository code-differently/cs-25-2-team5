package com.api.demo.services;

import com.api.demo.dtos.UserInviteDTO;
import com.api.demo.exceptions.UnauthorizedAccessException;
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
    user.setEmail(user.getEmail().strip().toLowerCase());
    user.setName(user.getName().strip());
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

  public List<User> getAllUsers() {
    return userRepository.findAll();
  }

  /*
   * Updates an event if the user is the organizer of that event.
   * @param userId         The ID of the user attempting to update the event.
   * @param eventId        The ID of the event to be updated.
   * @param updatedEvent   The event data with updated information.
   *
   * @return The updated EventModel.
   * @throws UnauthorizedAccessException if the user is not the organizer of the event.
   *
   * Uses @Transactional to ensure database integrity during the operation.
   */
  @Transactional
  public EventModel updateUserEvent(Long userId, Long eventId, EventModel updatedEvent) {
    // Validate that the user exists
    getUserById(userId);

    EventModel existingEvent = eventService.getEventById(eventId);

    // Check if the user is the organizer of this event
    if (!isUserOrganizerOfEvent(userId, existingEvent)) {
      throw new UnauthorizedAccessException("Only the event organizer can update this event");
    }

    return eventService.updateEvent(eventId, updatedEvent);
  }

  public void deleteEvent(Long id, Long userId) {
    EventModel event = eventService.getEventById(id);
    if (!isUserOrganizerOfEvent(userId, event)) {
      throw new UnauthorizedAccessException("Only the event organizer can delete this event");
    }
    eventService.deleteEvent(id);
  }

  private boolean isUserOrganizerOfEvent(Long userId, EventModel event) {
    return event.getOrganizer().getId().equals(userId);
  }

  public User getByClerkId(String clerkId) {
    return userRepository.findbyClerkId(clerkId).orElseThrow(()->new UserNotFoundException("User with id not found"));
  }

}
