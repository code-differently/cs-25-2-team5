package com.api.demo.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.api.demo.models.Location;

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
    public Location geocodeAddress(String address) {
        String url =
                UriComponentsBuilder.fromUriString(baseUrl)
                        .path("/search.php")
                        .queryParam("key", apiKey)
                        .queryParam("q", address)
                        .queryParam("format", "json")
                        .queryParam("limit", "1")
                        .build()
                        .toUriString();

        log.info("Geocoding address: {}", address);

        try {
            Location response = restTemplate.getForObject(url, Location.class);
            log.info("Successfully geocoded address: {}", address);
            return response;
        } catch (Exception e) {
            log.error("Failed to geocode address: {}", address, e);
            throw new RuntimeException("Geocoding failed for address: " + address, e);
        }
    }


}
