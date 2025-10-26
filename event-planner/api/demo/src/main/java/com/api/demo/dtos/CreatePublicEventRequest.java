package com.api.demo.dtos;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class CreatePublicEventRequest {
  private String title;
  private String description;
  private Boolean isPublic;
  private LocalDateTime startTime;
  private String address;
}
