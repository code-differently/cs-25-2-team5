package com.api.demo.exceptions;

public class EventGuestNotFoundException extends RuntimeException {
  public EventGuestNotFoundException(String message) {
    super(message);
  }
}
