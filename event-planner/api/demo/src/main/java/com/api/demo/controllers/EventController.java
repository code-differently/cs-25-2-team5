package com.api.demo.controllers;

import com.api.demo.models.EventModel;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/events")
public class EventController {

  @GetMapping
  public List<EventModel> getAllCommunityEvents() {
    // Placeholder implementation
    return List.of();
  }
}
