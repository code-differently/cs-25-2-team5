package com.team5.cbl.exceptions;

public class ComicNotFoundException extends IllegalArgumentException {
  public ComicNotFoundException(String message) {
    super(message);
  }
}
