package com.api.demo.controllers;

import com.api.demo.dtos.EventDTO;
import com.api.demo.dtos.LoginRequest;
import com.api.demo.dtos.UserDTO;
import com.api.demo.models.EventModel;
import com.api.demo.models.User;
import com.api.demo.services.UserService;
import jakarta.validation.Valid;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/users")
@Validated
@CrossOrigin("*")
public class UserController {

  public UserService userService;

  @Autowired
  public UserController(UserService userService) {
    this.userService = userService;
  }

  @GetMapping("")
  public List<UserDTO> getAllUsers() {
    List<User> users = userService.getAllUsers();
    List<UserDTO> userDTOs = new ArrayList<>();
    for (User user : users) {
      UserDTO userDTO =
          UserDTO.builder().id(user.getId()).name(user.getName()).email(user.getEmail()).build();
      userDTOs.add(userDTO);
    }
    return userDTOs;
  }

  @GetMapping("/{id}")
  public ResponseEntity<UserDTO> getUserById(@PathVariable Long id) {
    User user = userService.getUserById(id);
    UserDTO userDTO =
        UserDTO.builder().name(user.getName()).email(user.getEmail()).id(user.getId()).build();
    return ResponseEntity.ok(userDTO);
  }

  @GetMapping("/clerk/{id}")
  public ResponseEntity<UserDTO> getUserByClerkId(@PathVariable String clerkId) {

    User user = userService.getByClerkId(clerkId);
    UserDTO userDTO =
        UserDTO.builder().name(user.getName()).email(user.getEmail()).id(user.getId()).build();
    return ResponseEntity.ok(userDTO);
    
  }

  @PostMapping("")
  public ResponseEntity<UserDTO> createUser(@Valid @RequestBody User user) {
    User createdUser = userService.createUser(user);
    UserDTO userDTO =
        UserDTO.builder()
            .name(createdUser.getName())
            .email(createdUser.getEmail())
            .id(createdUser.getId())
            .build();
    return ResponseEntity.ok(userDTO);
  }

  @PostMapping("/{userId}/events")
  public ResponseEntity<EventDTO> createEventForUser(
      @PathVariable Long userId, @RequestBody EventModel event) {
    EventModel createdEvent = userService.createPublicEvent(event, userId);
    User organizer = createdEvent.getOrganizer();

    UserDTO organizerDTO =
        UserDTO.builder()
            .name(organizer.getName())
            .email(organizer.getEmail())
            .id(organizer.getId())
            .build();
    Set<UserDTO> guests = new HashSet<>();
    EventDTO model =
        EventDTO.builder()
            .title(createdEvent.getTitle())
            .description(createdEvent.getDescription())
            .startTime(createdEvent.getStartTime())
            .eventType(createdEvent.getIsPublic() ? "Community" : "Private")
            .organizer(organizerDTO)
            .guests(guests)
            .id(createdEvent.getId())
            .build();
    return ResponseEntity.ok(model);
  }

  @PutMapping("/{userId}/events/{eventId}")
  public ResponseEntity<EventDTO> updateUserEvent(
      @PathVariable Long userId,
      @PathVariable Long eventId,
      @RequestBody EventModel updatedEventInfo) {
    EventModel updatedEvent = userService.updateUserEvent(userId, eventId, updatedEventInfo);
    User organizer = updatedEvent.getOrganizer();

    UserDTO organizerDTO =
        UserDTO.builder()
            .name(organizer.getName())
            .email(organizer.getEmail())
            .id(organizer.getId())
            .build();
    Set<UserDTO> guests = new HashSet<>();
    EventDTO eventDTO =
        EventDTO.builder()
            .title(updatedEvent.getTitle())
            .description(updatedEvent.getDescription())
            .startTime(updatedEvent.getStartTime())
            .eventType(updatedEvent.getIsPublic() ? "Community" : "Private")
            .organizer(organizerDTO)
            .guests(guests)
            .build();
    return ResponseEntity.ok(eventDTO);
  }

  @DeleteMapping("/{userId}/events/{eventId}")
  public ResponseEntity<Void> deleteUserEvent(
      @Valid @PathVariable Long userId, @Valid @PathVariable Long eventId) {
    userService.deleteEvent(eventId, userId);
    return ResponseEntity.noContent().build();
  }

  @PostMapping("/login")
  public ResponseEntity<UserDTO> loginUser(@RequestBody LoginRequest loginRequest) {
    User user = userService.getUserByEmail(loginRequest.getEmail());

    if (user == null || !user.getPassword().equals(loginRequest.getPassword())) {
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    UserDTO userDTO =
        UserDTO.builder().id(user.getId()).name(user.getName()).email(user.getEmail()).build();

    return ResponseEntity.ok(userDTO);
  }
}
