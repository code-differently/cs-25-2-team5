package com.api.demo.services;

import com.api.demo.config.LocationIQConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Service
public class LocationIQService {

  private static final org.slf4j.Logger log =
      org.slf4j.LoggerFactory.getLogger(LocationIQService.class);

  private final LocationIQConfig locationConfig;
  private final RestTemplate restTemplate;

  @Autowired
  public LocationIQService(LocationIQConfig locationConfig, RestTemplate restTemplate) {
    this.locationConfig = locationConfig;
    this.restTemplate = restTemplate;
  }

  /**
   * Geocode an address to get latitude and longitude coordinates.
   *
   * @param address The address to geocode
   * @return JSON response from LocationIQ API
   */
  public String geocodeAddress(String address) {
    String url =
        UriComponentsBuilder.fromHttpUrl(locationConfig.getBaseUrl())
            .path("/search.php")
            .queryParam("key", locationConfig.getApiKey())
            .queryParam("q", address)
            .queryParam("format", "json")
            .queryParam("limit", "1")
            .build()
            .toUriString();

    log.info("Geocoding address: {}", address);

    try {
      String response = restTemplate.getForObject(url, String.class);
      log.info("Successfully geocoded address: {}", address);
      return response;
    } catch (Exception e) {
      log.error("Failed to geocode address: {}", address, e);
      throw new RuntimeException("Geocoding failed for address: " + address, e);
    }
  }

  /**
   * Reverse geocode coordinates to get address information.
   *
   * @param latitude The latitude coordinate
   * @param longitude The longitude coordinate
   * @return JSON response from LocationIQ API
   */
  public String reverseGeocode(double latitude, double longitude) {
    String url =
        UriComponentsBuilder.fromHttpUrl(locationConfig.getBaseUrl())
            .path("/reverse.php")
            .queryParam("key", locationConfig.getApiKey())
            .queryParam("lat", latitude)
            .queryParam("lon", longitude)
            .queryParam("format", "json")
            .build()
            .toUriString();

    log.info("Reverse geocoding coordinates: {}, {}", latitude, longitude);

    try {
      String response = restTemplate.getForObject(url, String.class);
      log.info("Successfully reverse geocoded coordinates: {}, {}", latitude, longitude);
      return response;
    } catch (Exception e) {
      log.error("Failed to reverse geocode coordinates: {}, {}", latitude, longitude, e);
      throw new RuntimeException(
          "Reverse geocoding failed for coordinates: " + latitude + ", " + longitude, e);
    }
  }

  public LocationIQConfig getLocationConfig() {
    return locationConfig;
  }
}
