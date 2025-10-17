package com.api.demo.models;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@Table(name = "event_guests")
public class EventGuest {

    @EmbeddedId
    private EventGuestKey eventGuestKey;

    @ManyToOne
    @MapsId("eventId")
    @JoinColumn(name = "event_id")
    private EventModel event;
    @ManyToOne
    @MapsId("guestId")
    @JoinColumn(name = "guest_id")
    private User guest;


    private RsvpStatus rsvpStatus;



}


