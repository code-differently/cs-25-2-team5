package com.api.demo.models;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/*
 * User entity representing a user in the event planning application.
 * Includes validation annotations and relationships to events.
 */
@Data // Lombok: generates getters, setters, toString, equals, hashCode
@AllArgsConstructor // Lombok: constructor with all fields
@NoArgsConstructor // Lombok: no-args constructor (required by JPA)
@Entity // Marks this class as a JPA entity
@Table(name = "users")
@EqualsAndHashCode(
    exclude = {
      "organizedEvents",
      "eventGuests"
    }) // Avoids circular references in equals and hashCode
public class User {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @NotBlank(message = "Name is required")
  private String name;

  @Email(message = "Email should be valid")
  @NotBlank(message = "Email is required")
  private String email;

  @Size(min = 8, message = "Password must be at least 8 characters long")
  private String password;

  @OneToMany(mappedBy = "guest",cascade = CascadeType.ALL)
  private Set<EventGuest> eventGuests;

  @OneToMany(mappedBy = "organizer", cascade = CascadeType.ALL)
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
