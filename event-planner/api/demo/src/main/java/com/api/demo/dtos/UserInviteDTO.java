package com.api.demo.dtos;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@AllArgsConstructor
@Builder
@Data
public class UserInviteDTO {
  private String email;
  private String name;
  private String description;
  private Boolean isPublic;
  private LocalDateTime startTime;
  private String title;
  private Long eventId;
  private String location;
  private String imageUrl;
}
