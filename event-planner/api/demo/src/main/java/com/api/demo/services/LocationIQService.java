package com.api.demo.services;

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
   * Get formatted address string from LocationIQ
   *
   * @param address Input address to geocode
   * @return Formatted address string or input address if API fails
   */
  public String geocodeAddress(String address) {
    try {
      URI uri =
          UriComponentsBuilder.fromUriString(baseUrl)
              .queryParam("q", address)
              .queryParam("key", apiKey) // LocationIQ uses "key", not "apiKey"
              .queryParam("format", "json")
              .queryParam("limit", 1)
              .build()
              .toUri();

      log.info("Making request to LocationIQ: {}", uri);

      // LocationIQ returns an array of results directly
      List<Map<String, Object>> response = restTemplate.getForObject(uri, List.class);

      if (response != null && !response.isEmpty()) {
        Map<String, Object> firstResult = response.get(0);
        String displayName = (String) firstResult.get("display_name");

        log.info("LocationIQ success: {} -> {}", address, displayName);
        return displayName != null ? displayName : address;
      }

      log.warn("LocationIQ returned empty response for: {}", address);
      return address; // Fallback to input

    } catch (Exception e) {
      log.error("LocationIQ API error for '{}': {}", address, e.getMessage());
      return address; // Fallback to input
    }
  }
}
