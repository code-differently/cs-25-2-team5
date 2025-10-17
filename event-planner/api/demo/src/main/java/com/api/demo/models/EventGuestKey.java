package com.api.demo.models;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;

@Embeddable
@Data
@AllArgsConstructor
public class EventGuestKey implements Serializable{
    
    @Column(name = "event_id")
    private Long eventId;
    @Column(name = "guest_id")
    private Long guestId;
}
