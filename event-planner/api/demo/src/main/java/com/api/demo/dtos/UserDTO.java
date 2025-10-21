package com.api.demo.dtos;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class UserDTO {
    private String name;
    private String email;
    

}
