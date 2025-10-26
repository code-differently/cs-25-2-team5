package com.api.demo.dtos;

import com.api.demo.models.EventModel;
import java.util.HashSet;
import java.util.Set;

public class DTOConverter {

  public static EventDTO mapToDTO(EventModel event) {
    // Handle null organizer case
    UserDTO organizerDTO = null;
    if (event.getOrganizer() != null) {
      organizerDTO =
          UserDTO.builder()
              .id(event.getOrganizer().getId())
              .name(event.getOrganizer().getName())
              .email(event.getOrganizer().getEmail())
              .build();
    }

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
}
