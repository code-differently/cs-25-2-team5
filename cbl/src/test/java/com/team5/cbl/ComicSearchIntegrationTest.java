package com.team5.cbl;

import static org.assertj.core.api.Assertions.assertThat;
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
    
    // Verify we get the expected number of results
    assertThat(results).hasSize(2);
    
    // Extract titles from the List results and use containsExactlyInAnyOrder to avoid order dependency
    var actualTitles = results.stream().map(Comic::getTitle).toList();
    
    assertThat(actualTitles)
        .containsExactlyInAnyOrder("The Dark Knight Returns", "Batman: Year One");
    
    // Verify all results actually contain the searched character
    results.forEach(comic -> {
      assertThat(comic.getMainCharacter().getName())
          .as("Comic '%s' should have Batman as main character", comic.getTitle())
          .isEqualTo("Batman");
    });
  }

  @Test
  @DisplayName("Search by creator (author) returns matching comics")
  void searchByCreator() {
    var results = library.filterByCreator("Frank Miller");
    
    // Verify we get the expected number of results
    assertThat(results).hasSize(2);
    
    // Extract titles from the List results and use containsExactlyInAnyOrder to avoid order dependency
    var actualTitles = results.stream().map(Comic::getTitle).toList();
    
    assertThat(actualTitles)
        .containsExactlyInAnyOrder("The Dark Knight Returns", "Batman: Year One");
    
    // Verify all results actually have the searched creator
    results.forEach(comic -> {
      assertThat(comic.getHeadWriter().getName())
          .as("Comic '%s' should be written by Frank Miller", comic.getTitle())
          .isEqualTo("Frank Miller");
    });
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

  @Test
  @DisplayName("Search by title - partial match should work")
  void searchByTitlePartialMatch() {
    var results = library.filterByComicTitle("Dark Knight");
    
    assertThat(results).hasSize(1);
    assertThat(results.get(0).getTitle()).isEqualTo("The Dark Knight Returns");
  }

  @Test
  @DisplayName("Search should be case insensitive")
  void searchCaseInsensitive() {
    // Test character search case insensitivity
    var upperCaseResults = library.filterByCharacter("BATMAN");
    var lowerCaseResults = library.filterByCharacter("batman");
    var mixedCaseResults = library.filterByCharacter("BaTmAn");
    
    // All should return the same results
    assertThat(upperCaseResults).hasSize(2);
    assertThat(lowerCaseResults).hasSize(2);
    assertThat(mixedCaseResults).hasSize(2);
    
    // Extract titles and compare using containsExactlyInAnyOrder
    var upperTitles = upperCaseResults.stream().map(Comic::getTitle).toList();
    var lowerTitles = lowerCaseResults.stream().map(Comic::getTitle).toList();
    var mixedTitles = mixedCaseResults.stream().map(Comic::getTitle).toList();
    
    assertThat(upperTitles)
        .containsExactlyInAnyOrderElementsOf(lowerTitles);
    assertThat(lowerTitles)
        .containsExactlyInAnyOrderElementsOf(mixedTitles);
  }

  @Test
  @DisplayName("Search results contain all required fields")
  void searchResultsContainAllRequiredFields() {
    var results = library.filterByCharacter("Batman");
    
    assertThat(results).isNotEmpty();
    
    results.forEach(comic -> {
      // Verify title is present and not empty
      assertThat(comic.getTitle())
          .as("Comic title should not be null or empty")
          .isNotNull()
          .isNotBlank();
      
      // Verify author/creator is present and not empty
      assertThat(comic.getHeadWriter())
          .as("Comic head writer should not be null")
          .isNotNull();
      assertThat(comic.getHeadWriter().getName())
          .as("Creator name should not be null or empty")
          .isNotNull()
          .isNotBlank();
      
      // Verify year is present and reasonable
      assertThat(comic.getRarityDetails())
          .as("Rarity details should not be null")
          .isNotNull();
      assertThat(comic.getRarityDetails().getYear())
          .as("Comic year should be present and reasonable")
          .isNotNull()
          .isAfter(Year.of(1900))
          .isBeforeOrEqualTo(Year.now());
      
      // Verify grade is present
      assertThat(comic.getRarityDetails().getGrade())
          .as("Comic grade should be a valid positive number")
          .isPositive()
          .isLessThanOrEqualTo(10.0);
    });
  }

  @Test
  @DisplayName("Multiple search operations should be consistent")
  void multipleSearchOperationsConsistency() {
    // Perform the same search multiple times
    var results1 = library.filterByCharacter("Batman");
    var results2 = library.filterByCharacter("Batman");
    var results3 = library.filterByCharacter("Batman");
    
    // All results should be identical
    assertThat(results1).hasSize(results2.size());
    assertThat(results2).hasSize(results3.size());
    
    // Extract titles and compare using containsExactlyInAnyOrder
    var titles1 = results1.stream().map(Comic::getTitle).toList();
    var titles2 = results2.stream().map(Comic::getTitle).toList();
    var titles3 = results3.stream().map(Comic::getTitle).toList();
    
    assertThat(titles1)
        .containsExactlyInAnyOrderElementsOf(titles2);
    assertThat(titles2)
        .containsExactlyInAnyOrderElementsOf(titles3);
  }
}