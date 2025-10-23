package com.api.demo.models;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Location {
    private Double latitude;
    private Double longitude;

    private String city;
    private String state;
    private String country;
    private String zipCode;
    private String formattedAddress;
    private String inputAddress;

    public boolean hasCoordinates() {
        return latitude != null && longitude != null;
    }
}
