package com.team5.cbl;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.team5.cbl.cbl_app.enums.Edition;
import com.team5.cbl.cbl_app.objects.RarityDetails;
import java.time.Year;
import org.junit.jupiter.api.Test;

public class RarityDetailsTest {

  @Test
  public void testRarityDetailsCreation() {
    RarityDetails details = new RarityDetails(Edition.SINGLE_ISSUE, 150000, 7.6, Year.of(2001));
    assertEquals(Edition.SINGLE_ISSUE, details.getEdition());
    assertEquals(150000, details.getPrintCount());
    assertEquals(7.6, details.getGrade());
    assertEquals(Year.of(2001), details.getYear());
  }

  @Test
  public void testEditionGetterAndSetter() {
    RarityDetails details = new RarityDetails(Edition.SINGLE_ISSUE, 150000, 7.6, Year.of(2001));
    assertEquals(Edition.SINGLE_ISSUE, details.getEdition());

    details.setEdition(Edition.COLLECTORS_EDITION);
    assertEquals(Edition.COLLECTORS_EDITION, details.getEdition());
  }

  @Test
  public void testPrintCountGetterAndSetter() {
    RarityDetails details = new RarityDetails(Edition.GRAPHIC_NOVEL, 150000, 7.6, Year.of(2001));
    assertEquals(150000, details.getPrintCount());

    details.setPrintCount(200000);
    assertEquals(200000, details.getPrintCount());
  }

  @Test
  public void testGradeGetterAndSetter() {
    RarityDetails details = new RarityDetails(Edition.DIGITAL_EDITION, 150000, 7.6, Year.of(2001));
    assertEquals(7.6, details.getGrade());

    details.setGrade(9.2);
    assertEquals(9.2, details.getGrade());
  }

  @Test
  public void testYearGetterAndSetter() {
    RarityDetails details = new RarityDetails(Edition.TRADE_PAPERBACK, 150000, 7.6, Year.of(2001));
    assertEquals(Year.of(2001), details.getYear());

    details.setYear(Year.of(2023));
    assertEquals(Year.of(2023), details.getYear());
  }
}
