package com.api.demo.dtos;

import com.api.demo.models.EventModel;
import com.api.demo.models.User;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class DTOConverter {

  public static EventDTO mapToDTO(EventModel event) {
    // Handle null organizer case
    UserDTO organizerDTO = mapToDTO(event.getOrganizer());

    Set<UserDTO> guests = new HashSet<>();
    EventDTO model =
        EventDTO.builder()
            .title(event.getTitle())
            .description(event.getDescription())
            .startTime(event.getStartTime())
            .eventType(event.getIsPublic() != null && event.getIsPublic() ? "Community" : "Private")
            .organizer(organizerDTO)
            .guests(guests)
            .id(event.getId())
            .address(event.getLocation())
            .imageURL(event.getImageURL())
            .build();
    return model;
  }

  public static UserDTO mapToDTO(User user) {
    UserDTO userDTO = null;
    if (user != null) {
      userDTO =
          UserDTO.builder().id(user.getId()).name(user.getName()).email(user.getEmail()).build();
    }
    return userDTO;
  }

  public static EventModel mapToModel(CreateEventWithGuestsRequest request) {
    EventModel createdEvent = new EventModel();
    createdEvent.setDescription(request.getDescription());
    createdEvent.setTitle(request.getTitle());
    createdEvent.setStartTime(request.getStartTime());
    createdEvent.setIsPublic(request.getIsPublic());
    createdEvent.setLocation(request.getAddress());
    return createdEvent;
  }

  public static EventDTO mapToDTO(UserInviteDTO userInvite) {
    return EventDTO.builder().title(userInvite.getTitle()).id(userInvite.getEventId()).build();
  }

  public static List<EventDTO> mapListToDTO(List<UserInviteDTO> list) {
    return list.stream().map(DTOConverter::mapToDTO).toList();
  }
}
