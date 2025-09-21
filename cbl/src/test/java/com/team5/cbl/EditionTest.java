package com.team5.cbl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.team5.cbl.cbl_app.enums.Edition;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.Test;

public class EditionTest {

  @Test
  public void testAllEditionValues() {
    Edition[] editions = Edition.values();
    assertEquals(7, editions.length);

    List<Edition> editionList = Arrays.asList(editions);
    assertTrue(editionList.contains(Edition.SINGLE_ISSUE));
    assertTrue(editionList.contains(Edition.TRADE_PAPERBACK));
    assertTrue(editionList.contains(Edition.GRAPHIC_NOVEL));
    assertTrue(editionList.contains(Edition.OMNIBUSE));
    assertTrue(editionList.contains(Edition.COLLECTORS_EDITION));
    assertTrue(editionList.contains(Edition.DIGITAL_EDITION));
    assertTrue(editionList.contains(Edition.ALTERNATE_COMIC));
  }

  @Test
  public void testDisplayNames() {
    assertEquals("Single Issues", Edition.SINGLE_ISSUE.getDisplayName());
    assertEquals("Trade Paperbacks", Edition.TRADE_PAPERBACK.getDisplayName());
    assertEquals("Graphic Novels", Edition.GRAPHIC_NOVEL.getDisplayName());
    assertEquals("Omnibuses", Edition.OMNIBUSE.getDisplayName());
    assertEquals("Collector's Edition", Edition.COLLECTORS_EDITION.getDisplayName());
    assertEquals("Digital Editions", Edition.DIGITAL_EDITION.getDisplayName());
    assertEquals("Alternate Comics", Edition.ALTERNATE_COMIC.getDisplayName());
  }

  @Test
  public void testEditionValueOf() {
    assertEquals(Edition.SINGLE_ISSUE, Edition.valueOf("SINGLE_ISSUES"));
    assertEquals(Edition.TRADE_PAPERBACK, Edition.valueOf("TRADE_PAPERBACKS"));
    assertEquals(Edition.GRAPHIC_NOVEL, Edition.valueOf("GRAPHIC_NOVELS"));
    assertEquals(Edition.OMNIBUSE, Edition.valueOf("OMNIBUSES"));
    assertEquals(Edition.COLLECTORS_EDITION, Edition.valueOf("COLLECTORS_EDITION"));
    assertEquals(Edition.DIGITAL_EDITION, Edition.valueOf("DIGITAL_EDITIONS"));
    assertEquals(Edition.ALTERNATE_COMIC, Edition.valueOf("ALTERNATE_COMICS"));
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
