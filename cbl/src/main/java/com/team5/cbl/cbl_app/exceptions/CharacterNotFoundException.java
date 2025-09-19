package com.team5.cbl.cbl_app.exceptions;

public class CharacterNotFoundException extends IllegalArgumentException {
  public CharacterNotFoundException(String message) {
    super(message);
  }
}
