package com.api.demo.dtos;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class EventDTO {

    private String title;
    private String description;
    private LocalDateTime startTime;
    private String eventType;
    private UserDTO organizer;
    private Set<UserDTO> guests;

    
}
