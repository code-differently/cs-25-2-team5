package com.team5.cbl;

import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.team5.cbl.cbl_app.enums.CompanyName;
import com.team5.cbl.cbl_app.enums.Edition;
import com.team5.cbl.cbl_app.enums.Genre;
import com.team5.cbl.cbl_app.exceptions.ComicNotFoundException;
import com.team5.cbl.cbl_app.objects.Character;
import com.team5.cbl.cbl_app.objects.Comic;
import com.team5.cbl.cbl_app.objects.ComicLibrary;
import com.team5.cbl.cbl_app.objects.Creator;
import com.team5.cbl.cbl_app.objects.Publisher;
import com.team5.cbl.cbl_app.objects.RarityDetails;
import java.time.Year;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * Integration test for User Story 1:
 * Search by character, author (creator), or title.
 * Verifies successful results and helpful "no results" messages.
 */
public class ComicSearchIntegrationTest {

  private ComicLibrary library;
  private List<Comic> seed;

  @BeforeEach
  void setUp() {
    seed = new ArrayList<>();

    List<Genre> gHero = List.of(Genre.SUPERHERO, Genre.ACTION);
    List<Genre> gSciFi = List.of(Genre.SCI_FI);

    Creator moore = new Creator("Alan Moore", 71);
    Creator miller = new Creator("Frank Miller", 67);

    Character bats = new Character("Batman", "Dark Knight of Gotham");
    Character manhattan = new Character("Dr. Manhattan", "Living tachyon battery");

    Publisher dc = new Publisher(CompanyName.DC_COMICS);
    Publisher indie = new Publisher(CompanyName.INDIE);

    Comic watchmen = new Comic(
        "Watchmen",
        gSciFi,
        new RarityDetails(Edition.SINGLE_ISSUES, 120, 9.6, Year.of(1986)),
        moore,
        manhattan,
        dc);

    Comic darkKnight = new Comic(
        "The Dark Knight Returns",
        gHero,
        new RarityDetails(Edition.TRADE_PAPERBACKS, 300, 9.2, Year.of(1986)),
        miller,
        bats,
        dc);

    Comic yearOne = new Comic(
        "Batman: Year One",
        gHero,
        new RarityDetails(Edition.SINGLE_ISSUES, 200, 9.0, Year.of(1987)),
        miller,
        bats,
        indie);

    seed.add(watchmen);
    seed.add(darkKnight);
    seed.add(yearOne);

    library = new ComicLibrary(seed);
  }

  @Test
  @DisplayName("Search by title returns a comic with title, author, year, grade")
  void searchByTitle() {
    var results = library.filterByComicTitle("Watchmen");
    assertEquals(1, results.size());

    Comic c = results.get(0);
    assertEquals("Watchmen", c.getTitle());
    assertNotNull(c.getHeadWriter());
    assertEquals("Alan Moore", c.getHeadWriter().getName());
    assertNotNull(c.getRarityDetails());
    assertEquals(Year.of(1986), c.getRarityDetails().getYear());
    assertEquals(9.6, c.getRarityDetails().getGrade(), 1e-9);
  }

  @Test
  @DisplayName("Search by character returns all matching comics")
  void searchByCharacter() {
    var results = library.filterByCharacter("Batman");
    var titles = results.stream().map(Comic::getTitle).toList();
    assertEquals(List.of("The Dark Knight Returns", "Batman: Year One"), titles);
  }

  @Test
  @DisplayName("Search by creator (author) returns matching comics")
  void searchByCreator() {
    var results = library.filterByCreator("Frank Miller");
    var titles = results.stream().map(Comic::getTitle).toList();
    assertEquals(List.of("The Dark Knight Returns", "Batman: Year One"), titles);
  }

  @Test
  @DisplayName("No results → helpful message for each filter type")
  void searchNoResultsShowsHelpfulMessage() {
    assertThatExceptionOfType(ComicNotFoundException.class)
        .isThrownBy(() -> library.filterByComicTitle("Nonexistent Title"))
        .withMessage("Comic title not found");

    assertThatExceptionOfType(ComicNotFoundException.class)
        .isThrownBy(() -> library.filterByCharacter("Not A Character"))
        .withMessage("Character not found");

    assertThatExceptionOfType(ComicNotFoundException.class)
        .isThrownBy(() -> library.filterByCreator("Unknown Author"))
        .withMessage("Creator not found");
  }
}
