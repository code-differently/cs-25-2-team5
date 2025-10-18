package com.api.demo.models;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class UserTest {

  @Test
  @DisplayName("Test custom constructor")
  public void testCustomConstructor() {
    User user = new User("Jane Doe", "jane@example.com", "password456");
    assertEquals("Jane Doe", user.getName());
    assertEquals("jane@example.com", user.getEmail());
    assertEquals("password456", user.getPassword());
  }

  @Test
  @DisplayName("Test email validation - valid email")
  public void testEmailValidation_Valid() {
    User user = new User("Test", "valid@example.com", "password123");
    user.setEmail("valid@example.com");
    assertEquals("valid@example.com", user.getEmail());
  }

  @Test
  @DisplayName("Test email validation - invalid email (no @)")
  public void testEmailValidation_Invalid() {
    User user = new User("Test", "valid@example.com", "password123");
    Exception exception =
        assertThrows(
            IllegalArgumentException.class,
            () -> {
              user.setEmail("invalidemail.com");
            });
    assertTrue(exception.getMessage().contains("Invalid email format"));
  }

  @Test
  @DisplayName("Test email validation - null email")
  public void testEmailValidation_Null() {
    User user = new User("Test", "valid@example.com", "password123");
    Exception exception =
        assertThrows(
            IllegalArgumentException.class,
            () -> {
              user.setEmail(null);
            });
    assertTrue(exception.getMessage().contains("Invalid email format"));
  }

  @Test
  @DisplayName("Test password validation - valid password")
  public void testPasswordValidation_Valid() {
    User user = new User("Test", "test@example.com", "password123");
    user.setPassword("password123");
    assertEquals("password123", user.getPassword());
  }

  @Test
  @DisplayName("Test password validation - too short")
  public void testPasswordValidation_TooShort() {
    User user = new User("Test", "test@example.com", "password123");
    Exception exception =
        assertThrows(
            IllegalArgumentException.class,
            () -> {
              user.setPassword("12345");
            });
    assertTrue(exception.getMessage().contains("Password must be at least 6 characters long"));
  }

  @Test
  @DisplayName("Test password validation - null password")
  public void testPasswordValidation_Null() {
    User user = new User("Test", "test@example.com", "password123");
    Exception exception =
        assertThrows(
            IllegalArgumentException.class,
            () -> {
              user.setPassword(null);
            });
    assertTrue(exception.getMessage().contains("Password must be at least 6 characters long"));
  }
}
