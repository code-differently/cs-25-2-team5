package com.api.demo.models;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.Set;



import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data // Lombok: generates getters, setters, toString, equals, hashCode
@AllArgsConstructor // Lombok: constructor with all fields
@NoArgsConstructor // Lombok: no-args constructor (required by JPA)
@Entity
@Table(name = "users") // Marks this class as a JPA entity
public class User {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  @NonNull
  private String name;
  @NonNull
  private String email;
  @NonNull
  private String password;

  @OneToMany(mappedBy = "guest")
  private Set<EventGuest> eventGuests;

  @OneToMany(fetch = FetchType.LAZY, mappedBy = "organizer")
  private Set<EventModel> organizedEvents;



  // Custom constructor (skip id, since DB will generate it)
  public User(String name, String email, String password) {
    this.name = name;
    this.email = email;
    this.password = password;
  }

  // Example of custom validation logic
  public void setEmail(String email) {
    if (email == null || !email.contains("@")) {
      throw new IllegalArgumentException("Invalid email format");
    }
    this.email = email;
  }

  public void setPassword(String password) {
    if (password == null || password.length() < 6) {
      throw new IllegalArgumentException("Password must be at least 6 characters long");
    }
    this.password = password;
  }
}
