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
  private Boolean is_public;
  private LocalDateTime start_time;
  private String title;
}
