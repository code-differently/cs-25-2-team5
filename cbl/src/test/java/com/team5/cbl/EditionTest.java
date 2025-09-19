package com.team5.cbl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.Test;

public class EditionTest {

  @Test
  public void testAllEditionValues() {
    Edition[] editions = Edition.values();
    assertEquals(7, editions.length);

    List<Edition> editionList = Arrays.asList(editions);
    assertTrue(editionList.contains(Edition.SINGLE_ISSUES));
    assertTrue(editionList.contains(Edition.TRADE_PAPERBACKS));
    assertTrue(editionList.contains(Edition.GRAPHIC_NOVELS));
    assertTrue(editionList.contains(Edition.OMNIBUSES));
    assertTrue(editionList.contains(Edition.COLLECTORS_EDITION));
    assertTrue(editionList.contains(Edition.DIGITAL_EDITIONS));
    assertTrue(editionList.contains(Edition.ALTERNATE_COMICS));
  }

  @Test
  public void testDisplayNames() {
    assertEquals("Single Issues", Edition.SINGLE_ISSUES.getDisplayName());
    assertEquals("Trade Paperbacks", Edition.TRADE_PAPERBACKS.getDisplayName());
    assertEquals("Graphic Novels", Edition.GRAPHIC_NOVELS.getDisplayName());
    assertEquals("Omnibuses", Edition.OMNIBUSES.getDisplayName());
    assertEquals("Collector's Edition", Edition.COLLECTORS_EDITION.getDisplayName());
    assertEquals("Digital Editions", Edition.DIGITAL_EDITIONS.getDisplayName());
    assertEquals("Alternate Comics", Edition.ALTERNATE_COMICS.getDisplayName());
  }

  @Test
  public void testEditionValueOf() {
    assertEquals(Edition.SINGLE_ISSUES, Edition.valueOf("SINGLE_ISSUES"));
    assertEquals(Edition.TRADE_PAPERBACKS, Edition.valueOf("TRADE_PAPERBACKS"));
    assertEquals(Edition.GRAPHIC_NOVELS, Edition.valueOf("GRAPHIC_NOVELS"));
    assertEquals(Edition.OMNIBUSES, Edition.valueOf("OMNIBUSES"));
    assertEquals(Edition.COLLECTORS_EDITION, Edition.valueOf("COLLECTORS_EDITION"));
    assertEquals(Edition.DIGITAL_EDITIONS, Edition.valueOf("DIGITAL_EDITIONS"));
    assertEquals(Edition.ALTERNATE_COMICS, Edition.valueOf("ALTERNATE_COMICS"));
  }

  @Test
  public void testDisplayNameNotNull() {
    for (Edition edition : Edition.values()) {
      assertNotNull(edition.getDisplayName());
      assertTrue(edition.getDisplayName().length() > 0);
    }
  }

  @Test
  public void testToStringConsistentWithDisplayName() {
    for (Edition edition : Edition.values()) {
      assertEquals(edition.getDisplayName(), edition.toString());
    }
  }
}
