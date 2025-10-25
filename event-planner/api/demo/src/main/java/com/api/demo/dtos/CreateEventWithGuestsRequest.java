package com.api.demo.dtos;

import com.api.demo.models.EventModel;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO for creating an event with guests request. Contains event details and guest email addresses.
 * The organizer ID comes from the path variable (authenticated user context).
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateEventWithGuestsRequest {

  private EventModel event;
  private Set<String> guestEmails;
}
