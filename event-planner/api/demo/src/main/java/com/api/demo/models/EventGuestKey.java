package com.api.demo.models;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/*
 * Composite key for EventGuest entity representing the association between an event and a guest.
 */
@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
public class EventGuestKey implements Serializable {

    @Column(name = "event_id")
    private Long eventId;

    @Column(name = "guest_id")
    private Long guestId;
}
