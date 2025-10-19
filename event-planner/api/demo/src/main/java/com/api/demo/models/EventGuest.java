package com.api.demo.models;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Data
@NoArgsConstructor
@Table(name = "event_guests")

public class EventGuest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
   
   @ManyToOne
    private User user;

    @ManyToOne
    private EventModel event;

}
