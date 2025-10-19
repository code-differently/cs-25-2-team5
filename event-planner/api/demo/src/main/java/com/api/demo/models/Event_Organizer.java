package com.api.demo.models;

import java.util.Set;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;

@Entity
public class Event_Organizer extends User {
   
    @OneToMany(mappedBy = "organizer")
    private Set<EventModel> organizedEvents;

}
