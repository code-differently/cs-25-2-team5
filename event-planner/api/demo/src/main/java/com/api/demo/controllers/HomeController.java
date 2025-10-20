package com.api.demo.controllers;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {

  @GetMapping("/")
  public Map<String, Object> index() {
    // Get all available routes in the application
    // Build response with welcome message and routes
    Map<String, Object> response = new LinkedHashMap<>();
    response.put("message", "🚀 Welcome to The Evynt Event Planning API");
    response.put("version", "v1.0");
    response.put("description", "Event planning application with user management and event creation");
    
    return response;
  }

}
