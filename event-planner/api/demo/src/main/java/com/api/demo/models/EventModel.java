package com.api.demo.models;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import java.util.Set;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@Table(name = "events")
public class EventModel {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String title;

  private String description;

  private Boolean isPublic;

  private LocalDateTime startTime;

  @OneToMany(mappedBy = "event")
  private Set<EventGuest> eventGuests;

  // lazy loading to prevent fetching organizer details unless needed to avoid circular references
  @ManyToOne(fetch = FetchType.LAZY)
  private User organizer;
}
