package com.team5.cbl.cbl_app.enums;

public enum CompanyName {
  MARVEL_COMICS("Marvel Comics"),
  DC_COMICS("DC Comics"),
  IMAGE_COMICS("Image Comics"),
  DARK_HORSE_COMICS("Dark Horse Comics"),
  IDW_PUBLISHING("IDW Publishing"),
  VALIANT_ENTERTAINMENT("Valiant Entertainment"),
  BOOM_STUDIOS("Boom Studios"),
  INDIE("Independent");

  private final String displayName;

  CompanyName(String displayName) {
    this.displayName = displayName;
  }
}
