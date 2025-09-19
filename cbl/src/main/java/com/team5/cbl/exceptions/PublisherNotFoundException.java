package com.team5.cbl.exceptions;

public class PublisherNotFoundException extends IllegalArgumentException {
  public PublisherNotFoundException(String message) {
    super(message);
  }
}
