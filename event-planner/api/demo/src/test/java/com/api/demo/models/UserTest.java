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
  @DisplayName("Test Lombok generated setters and getters work")
  public void testLombokSettersAndGetters() {
    User user = new User("Test", "valid@example.com", "password123");
    user.setEmail("newemail@example.com");
    user.setPassword("newpassword123");
    assertEquals("newemail@example.com", user.getEmail());
    assertEquals("newpassword123", user.getPassword());
  }

  @Test
  @DisplayName("Test Lombok default constructor")
  public void testLombokDefaultConstructor() {
    User user = new User();
    user.setName("Test User");
    user.setEmail("test@example.com");
    user.setPassword("testpassword");

    assertEquals("Test User", user.getName());
    assertEquals("test@example.com", user.getEmail());
    assertEquals("testpassword", user.getPassword());
  }

  // Note: Jakarta validation tests would be tested in integration tests
  // where the validation context is available
}
