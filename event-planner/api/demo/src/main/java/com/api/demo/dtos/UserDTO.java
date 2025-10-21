package com.api.demo.dtos;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Builder
@Data
public class UserDTO {
    private String name;
    private String email;


}
