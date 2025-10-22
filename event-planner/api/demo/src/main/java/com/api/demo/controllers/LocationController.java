package com.api.demo.controllers;

import com.api.demo.services.LocationIQService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * REST Controller for location-related operations.cd
 * /workspaces/cs-25-2-team5/event-planner/api/demo
 *
 * <p># Build the project ./gradlew clean build
 *
 * <p># Run the application ./gradlew bootRun
 */
@RestController
@RequestMapping("/api/location")
public class LocationController {

  private static final org.slf4j.Logger log =
      org.slf4j.LoggerFactory.getLogger(LocationController.class);

  private final LocationIQService locationService;

  @Autowired
  public LocationController(LocationIQService locationService) {
    this.locationService = locationService;
  }

  /**
   * Geocode an address to get coordinates.
   *
   * @param address The address to geocode
   * @return LocationIQ response with coordinates
   */
  @GetMapping("/geocode")
  public ResponseEntity<String> geocodeAddress(@RequestParam String address) {
    log.info("Received geocoding request for address: {}", address);

    try {
      String result = locationService.geocodeAddress(address);
      return ResponseEntity.ok(result);
    } catch (Exception e) {
      log.error("Error geocoding address: {}", address, e);
      return ResponseEntity.internalServerError()
          .body("{\"error\":\"Failed to geocode address: " + address + "\"}");
    }
  }

  /**
   * Reverse geocode coordinates to get address.
   *
   * @param lat Latitude coordinate
   * @param lon Longitude coordinate
   * @return LocationIQ response with address information
   */
  @GetMapping("/reverse")
  public ResponseEntity<String> reverseGeocode(@RequestParam double lat, @RequestParam double lon) {
    log.info("Received reverse geocoding request for coordinates: {}, {}", lat, lon);

    try {
      String result = locationService.reverseGeocode(lat, lon);
      return ResponseEntity.ok(result);
    } catch (Exception e) {
      log.error("Error reverse geocoding coordinates: {}, {}", lat, lon, e);
      return ResponseEntity.internalServerError()
          .body("{\"error\":\"Failed to reverse geocode coordinates: " + lat + ", " + lon + "\"}");
    }
  }
}
