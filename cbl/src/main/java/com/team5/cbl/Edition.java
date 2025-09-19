package com.team5.cbl;

public enum Edition {
  SINGLE_ISSUES("Single Issues"),
  TRADE_PAPERBACKS("Trade Paperbacks"),
  GRAPHIC_NOVELS("Graphic Novels"),
  OMNIBUSES("Omnibuses"),
  COLLECTORS_EDITION("Collector's Edition"),
  DIGITAL_EDITIONS("Digital Editions"),
  ALTERNATE_COMICS("Alternate Comics");

  private final String displayName;

  Edition(String displayName) {
    this.displayName = displayName;
  }

  public String getDisplayName() {
    return displayName;
  }

  @Override
  public String toString() {
    return displayName;
  }
}
