package com.api.demo.services;

import com.api.demo.models.Location;
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
    private final LocationConverter locationConverter;

    @Autowired
    public LocationIQService(RestTemplate restTemplate, LocationConverter locationConverter) {
        this.restTemplate = restTemplate;
        this.locationConverter = locationConverter;
    }

    /**
     * Geocode an address to get latitude and longitude coordinates.
     *
     * @param address The address to geocode
     * @return JSON response from LocationIQ API
     */
    public String geocodeAddress(String address) {
        String url =
                UriComponentsBuilder.fromHttpUrl(baseUrl)
                        .path("/search.php")
                        .queryParam("key", apiKey)
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
                UriComponentsBuilder.fromHttpUrl(baseUrl)
                        .path("/reverse.php")
                        .queryParam("key", apiKey)
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

    public Location geocodeToLocation(String address) {
        log.info("Geocoding address to Location object: {}", address);

        try {
            String jsonResponse = geocodeAddress(address);
            return locationConverter.fromLocationIQ(jsonResponse, address);
        } catch (Exception e) {
            log.error("Failed to geocode address: {}", address, e);
            Location emptyLocation = new Location();
            emptyLocation.setInputAddress(address);
            return emptyLocation;
        }
    }
}
