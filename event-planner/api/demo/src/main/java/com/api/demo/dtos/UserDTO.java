package com.api.demo.dtos;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
/*
 * Data Transfer Object for User entity to expose limited user information.
 */
public class UserDTO {
  private String name;
  private String email;
}
