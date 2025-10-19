package com.api.demo.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data // Lombok: generates getters, setters, toString, equals, hashCode
@AllArgsConstructor // Lombok: constructor with all fields
@NoArgsConstructor // Lombok: no-args constructor (required by JPA)
@Entity
@Table(name = "users") // Marks this class as a JPA entity
@Inheritance

public class User {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;

  private String name;
  private String email;
  private String password;

  @OneToMany(mappedBy = "guest")
  private Set<EventGuest> eventGuests;

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
