package com.team5.cbl.cbl_app.enums;

public enum Edition {
  SINGLE_ISSUE("Single Issues"),
  TRADE_PAPERBACK("Trade Paperbacks"),
  GRAPHIC_NOVEL("Graphic Novels"),
  OMNIBUS("Omnibuses"),
  COLLECTORS_EDITION("Collector's Edition"),
  DIGITAL_EDITION("Digital Editions"),
  ALTERNATE_COMIC("Alternate Comics"),
  FIRST_EDITION("First Edition"),
  SECOND_EDITION("Second Edition");

  private final String displayName;

  Edition(String displayName) {
    this.displayName = displayName;
  }

  public String getDisplayName() {
    return displayName;
  }
}
