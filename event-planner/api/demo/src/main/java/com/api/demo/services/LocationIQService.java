package com.api.demo.services;

import com.api.demo.models.Location;
import java.net.URI;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Service
public class LocationIQService {

  private static final org.slf4j.Logger log =
      org.slf4j.LoggerFactory.getLogger(LocationIQService.class);

  @Value("${locationiq.api.key}")
  private String apiKey;

  @Value("${locationiq.api.base-url}")
  private String baseUrl;

  private final RestTemplate restTemplate;

  @Autowired
  public LocationIQService(RestTemplate restTemplate) {
    this.restTemplate = restTemplate;
  }

  /**
   * Geocode an address to get latitude and longitude coordinates.
   *
   * @param address The address to geocode
   * @return JSON response from LocationIQ API
   */
  private List<Map<String, Object>> fetchLocationData(String address) {
    try {
      URI uri =
          UriComponentsBuilder.fromUriString(baseUrl)
              .queryParam("q", address)
              .queryParam("apiKey", apiKey)
              .queryParam("limit", 1)
              .build()
              .toUri();

      log.info("Making request to LocationIQ: {}", uri);
      Map<String, Object> response = restTemplate.getForObject(uri, Map.class);
      log.info("LocationIQ response: {}", response);

      if (response == null || !response.containsKey("items")) return null;

      List<Map<String, Object>> items = (List<Map<String, Object>>) response.get("items");
      if (items.isEmpty()) return null;

      Map<String, Object> item = items.get(0);

      Map<String, Object> position = (Map<String, Object>) item.get("position");
      Map<String, Object> addressMap = (Map<String, Object>) item.get("address");
      items.add(addressMap);
      items.add(position);

      return items;
    } catch (Exception e) {
      log.error("Error calling LocationIQ API: {}", e.getMessage());
      log.error("Full error: ", e);
      return null; // Return null to trigger fallback
    }
  }

  public Location geocodeAddress(String address) {
    List<Map<String, Object>> locationData = fetchLocationData(address);
    if (locationData == null || locationData.size() < 3) {
      log.warn("LocationIQ API failed or returned no data for address: {}. Using fallback location.", address);
      
      // Return a fallback location so the event can still be created
      return new Location(
          40.7128, -74.0060, // Default to NYC coordinates
          "New York", "NY", "United States", 
          "10001", address, address);
    }

    Map<String, Object> addressMap = locationData.get(1);
    Map<String, Object> position = locationData.get(2);

    Double latitude = (Double) position.get("lat");
    Double longitude = (Double) position.get("lng");
    String city = (String) addressMap.get("city");
    String state = (String) addressMap.get("state");
    String country = (String) addressMap.get("countryName");
    String zipCode = (String) addressMap.get("postalCode");
    String formattedAddress = (String) addressMap.get("label");

    return new Location(
        latitude, longitude, city, state, country, zipCode, formattedAddress, address);
  }
}
