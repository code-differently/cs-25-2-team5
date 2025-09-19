package com.team5.cbl.exceptions;

public class CharacterNotFoundException extends IllegalArgumentException {
  public CharacterNotFoundException(String message) {
    super(message);
  }
}
