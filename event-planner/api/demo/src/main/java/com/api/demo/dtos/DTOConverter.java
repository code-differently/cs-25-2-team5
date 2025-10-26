package com.api.demo.dtos;

import java.util.HashSet;
import java.util.Set;

import com.api.demo.models.EventModel;


public class DTOConverter {

    public static EventDTO mapToDTO(EventModel event) {

    UserDTO organizerDTO =
        UserDTO.builder()
            .id(event.getOrganizer().getId())
            .name(event.getOrganizer().getName())
            .email(event.getOrganizer().getEmail())
            .build();
    Set<UserDTO> guests = new HashSet<>();
    EventDTO model =
        EventDTO.builder()
            .title(event.getTitle())
            .description(event.getDescription())
            .startTime(event.getStartTime())
            .eventType(event.getIsPublic() ? "Community" : "Private")
            .organizer(organizerDTO)
            .guests(guests)
            .id(event.getId())
            .build();
    return model;

  }

}
