package com.api.demo.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import java.util.Set;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@Table(name = "events")
@EqualsAndHashCode(exclude = {"eventGuests", "organizer"})
public class EventModel {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String title;
  private String description;
  private Boolean isPublic;
  private LocalDateTime startTime;

  @Embedded private Location location;

  @OneToMany(mappedBy = "event", cascade = CascadeType.ALL)
  private Set<EventGuest> eventGuests;

  @ManyToOne @JsonBackReference private User organizer;
}
