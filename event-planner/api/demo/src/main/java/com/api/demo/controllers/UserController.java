package com.api.demo.controllers;

import com.api.demo.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.api.demo.dtos.UserDTO;
import com.api.demo.services.UserService;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    public UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<UserDTO> getUserById(@PathVariable Long id) {
        User user = userService.getUserById(id);
        UserDTO userDTO = UserDTO.builder()
                .name(user.getName())
                .email(user.getEmail())
                .build();
        return ResponseEntity.ok(userDTO);
    }

    @PostMapping("/users")
    public ResponseEntity<UserDTO> createUser(@RequestBody User user) {
        User createdUser = userService.createUser(user);
        UserDTO userDTO = UserDTO.builder()
                .name(createdUser.getName())
                .email(createdUser.getEmail())
                .build();
        return ResponseEntity.ok(userDTO);
    }
}