package com.api.demo.models;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class EventGuest {
    @EmbeddedId
    private EventGuestKey eventGuestKey;

    @ManyToOne
    @MapsId("eventId")
    private EventModel event;
    @ManyToOne
    @MapsId("guestId")
    private User guest;

    private RsvpStatus rsvpStatus;



}


