package com.team5.cbl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.Year;
import org.junit.jupiter.api.Test;

public class RarityDetailsTest {
  @Test
  public void testRarityDetailsCreation() {
    RarityDetails details =
        new RarityDetails(RarityDetails.Edition.SINGLE_ISSUES, 150000, 7.6, Year.of(2001));
    assertEquals(RarityDetails.Edition.SINGLE_ISSUES, details.getEdition());
    assertEquals(150000, details.getPrintCount());
    assertEquals(7.6, details.getGrade());
    assertEquals(Year.of(2001), details.getYear());
  }

  @Test
  public void testGetEdition() {
    RarityDetails details =
        new RarityDetails(RarityDetails.Edition.TRADE_PAPERBACKS, 150000, 7.6, Year.of(2001));
    assertEquals(RarityDetails.Edition.TRADE_PAPERBACKS, details.getEdition());
  }

  @Test
  public void testSetEdition() {
    RarityDetails details =
        new RarityDetails(RarityDetails.Edition.SINGLE_ISSUES, 150000, 7.6, Year.of(2001));
    details.setEdition(RarityDetails.Edition.COLLECTORS_EDITION);
    assertEquals(RarityDetails.Edition.COLLECTORS_EDITION, details.getEdition());
  }

  @Test
  public void testGetPrintCount() {
    RarityDetails details =
        new RarityDetails(RarityDetails.Edition.GRAPHIC_NOVELS, 150000, 7.6, Year.of(2001));
    assertEquals(150000, details.getPrintCount());
  }

  @Test
  public void testSetPrintCount() {
    RarityDetails details =
        new RarityDetails(RarityDetails.Edition.OMNIBUSES, 150000, 7.6, Year.of(2001));
    details.setPrintCount(200000);
    assertEquals(200000, details.getPrintCount());
  }

  @Test
  public void testGetGrade() {
    RarityDetails details =
        new RarityDetails(RarityDetails.Edition.DIGITAL_EDITIONS, 150000, 7.6, Year.of(2001));
    assertEquals(7.6, details.getGrade());
  }

  @Test
  public void testSetGrade() {
    RarityDetails details =
        new RarityDetails(RarityDetails.Edition.ALTERNATE_COMICS, 150000, 7.6, Year.of(2001));
    details.setGrade(9.2);
    assertEquals(9.2, details.getGrade());
  }

  @Test
  public void testGetYear() {
    RarityDetails details =
        new RarityDetails(RarityDetails.Edition.SINGLE_ISSUES, 150000, 7.6, Year.of(2001));
    assertEquals(Year.of(2001), details.getYear());
  }

  @Test
  public void testSetYear() {
    RarityDetails details =
        new RarityDetails(RarityDetails.Edition.TRADE_PAPERBACKS, 150000, 7.6, Year.of(2001));
    details.setYear(Year.of(2023));
    assertEquals(Year.of(2023), details.getYear());
  }

  @Test
  public void testEditionDisplayName() {
    assertEquals("Single Issues", RarityDetails.Edition.SINGLE_ISSUES.getDisplayName());
    assertEquals("Trade Paperbacks", RarityDetails.Edition.TRADE_PAPERBACKS.getDisplayName());
    assertEquals("Collector's Edition", RarityDetails.Edition.COLLECTORS_EDITION.getDisplayName());
  }

  @Test
  public void testEditionToString() {
    assertEquals("Graphic Novels", RarityDetails.Edition.GRAPHIC_NOVELS.toString());
    assertEquals("Digital Editions", RarityDetails.Edition.DIGITAL_EDITIONS.toString());
  }

  @Test
  public void testAllEditionValues() {
    RarityDetails.Edition[] editions = RarityDetails.Edition.values();
    assertEquals(7, editions.length);
    assertTrue(java.util.Arrays.asList(editions).contains(RarityDetails.Edition.OMNIBUSES));
    assertTrue(java.util.Arrays.asList(editions).contains(RarityDetails.Edition.ALTERNATE_COMICS));
  }
}
