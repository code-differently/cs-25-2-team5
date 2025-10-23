package com.api.demo.services;

import com.api.demo.models.Location;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

@Service
public class LocationConverter {

    private static final org.slf4j.Logger log =
            org.slf4j.LoggerFactory.getLogger(LocationConverter.class);

    private final ObjectMapper objectMapper = new ObjectMapper();

    public Location fromLocationIQ(String locationIQJson, String originalAddress) {
        try {
            JsonNode jsonArray = objectMapper.readTree(locationIQJson);

            if (!jsonArray.isArray() || jsonArray.size() == 0) {
                return createEmptyLocation(originalAddress);
            }

            JsonNode firstResult = jsonArray.get(0);
            return convertToLocation(firstResult, originalAddress);

        } catch (Exception e) {
            log.error("Failed to parse LocationIQ response", e);
            return createEmptyLocation(originalAddress);
        }
    }

    private Location convertToLocation(JsonNode data, String originalAddress) {
        Location location = new Location();
        location.setInputAddress(originalAddress);

        // Get coordinates
        if (data.has("lat")) {
            location.setLatitude(data.get("lat").asDouble());
        }
        if (data.has("lon")) {
            location.setLongitude(data.get("lon").asDouble());
        }

        // Get formatted address
        if (data.has("display_name")) {
            location.setFormattedAddress(data.get("display_name").asText());
        }

        // Get address parts (if available)
        if (data.has("address")) {
            JsonNode address = data.get("address");
            if (address.has("city")) location.setCity(address.get("city").asText());
            if (address.has("state")) location.setState(address.get("state").asText());
            if (address.has("country")) location.setCountry(address.get("country").asText());
            if (address.has("postcode")) location.setZipCode(address.get("postcode").asText());
        }

        return location;
    }

    private Location createEmptyLocation(String originalAddress) {
        Location location = new Location();
        location.setInputAddress(originalAddress);
        return location;
    }
}
