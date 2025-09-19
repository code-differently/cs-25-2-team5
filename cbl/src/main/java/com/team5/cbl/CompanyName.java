package com.team5.cbl;

public enum CompanyName {
  MARVEL_COMICS("Marvel Comics"),
  DC_COMICS("DC Comics"),
  IMAGE_COMICS("Image Comics"),
  DARK_HORSE_COMICS("Dark Horse Comics"),
  IDW_PUBLISHING("IDW Publishing"),
  VALIANT_ENTERTAINMENT("Valiant Entertainment"),
  BOOM_STUDIOS("Boom Studios");

  private final String displayName;

  CompanyName(String displayName) {
    this.displayName = displayName;
  }
}
