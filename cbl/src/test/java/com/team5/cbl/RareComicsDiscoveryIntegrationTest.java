package com.team5.cbl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.team5.cbl.cbl_app.enums.CompanyName;
import com.team5.cbl.cbl_app.enums.Edition;
import com.team5.cbl.cbl_app.enums.Genre;
import com.team5.cbl.cbl_app.objects.Character;
import com.team5.cbl.cbl_app.objects.Comic;
import com.team5.cbl.cbl_app.objects.ComicLibrary;
import com.team5.cbl.cbl_app.objects.Creator;
import com.team5.cbl.cbl_app.objects.Publisher;
import com.team5.cbl.cbl_app.objects.RarityDetails;
import java.time.Year;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

/**
 * Integration test for User Story 2: Discover and filter for the rarest comics available to grow
 * collection with high-value, hard-to-find issues. Tests sorting by rarity/market value and proper
 * rarity detail labeling using existing ComicLibrary methods.
 */
public class RareComicsDiscoveryIntegrationTest {

  @Nested
  @DisplayName("User Story 2: Rare comics discovery")
  class RareComicsDiscovery {

    private ComicLibrary library;
    private List<Comic> seed;

    @BeforeEach
    void setUpUS2() {
      seed = new ArrayList<>();

      List<Genre> superhero = List.of(Genre.SUPERHERO, Genre.ACTION);
      List<Genre> horror = List.of(Genre.HORROR);

      Creator leeKirby = new Creator("Stan Lee & Jack Kirby", 65);
      Creator moore = new Creator("Alan Moore", 71);
      Creator miller = new Creator("Frank Miller", 67);
      Creator kane = new Creator("Bob Kane", 83);

      Character spiderman = new Character("Spider-Man", "Your friendly neighborhood Spider-Man");
      Character batman = new Character("Batman", "The Dark Knight");
      Character swampThing = new Character("Swamp Thing", "Elemental protector of nature");

      // Use correct enum constants from your project
      Publisher marvel = new Publisher(CompanyName.MARVEL_COMICS);
      Publisher dc = new Publisher(CompanyName.DC_COMICS);

      // Use correct Edition enum constants
      Comic amazingFantasy15 =
          new Comic(
              "Amazing Fantasy #15",
              superhero,
              100,
              new RarityDetails(Edition.FIRST_EDITION, 50, 9.8, Year.of(1962)),
              leeKirby,
              spiderman,
              marvel);

      Comic incredibleHulk1 =
          new Comic(
              "The Incredible Hulk #1",
              superhero,
              100,
              new RarityDetails(Edition.FIRST_EDITION, 150, 9.4, Year.of(1962)),
              leeKirby,
              spiderman,
              marvel);

      Comic batman1 =
          new Comic(
              "Batman #1",
              superhero,
              100,
              new RarityDetails(Edition.FIRST_EDITION, 500, 8.5, Year.of(1940)),
              kane,
              batman,
              dc);

      Comic swampThing21 =
          new Comic(
              "Saga of the Swamp Thing #21",
              horror,
              100,
              new RarityDetails(Edition.FIRST_EDITION, 1200, 9.0, Year.of(1984)),
              moore,
              swampThing,
              dc);

      Comic darkKnightTpb =
          new Comic(
              "The Dark Knight Returns TPB",
              superhero,
              100,
              new RarityDetails(Edition.TRADE_PAPERBACK, 25000, 9.2, Year.of(2016)),
              miller,
              batman,
              dc);

      Comic variantCover =
          new Comic(
              "Amazing Spider-Man #1 Variant",
              superhero,
              100,
              new RarityDetails(Edition.SECOND_EDITION, 100, 9.6, Year.of(2022)),
              leeKirby,
              spiderman,
              marvel);

      seed.add(amazingFantasy15);
      seed.add(incredibleHulk1);
      seed.add(batman1);
      seed.add(swampThing21);
      seed.add(darkKnightTpb);
      seed.add(variantCover);

      library = new ComicLibrary(seed);
    }

    @Test
    @DisplayName("Sort by rarity: lowest print count first")
    void sortByRarity_PrintCountAscending() {
      // Use existing getComics() method and sort by print count using streams
      List<Comic> sorted =
          library.getComics().stream()
              .sorted(
                  (c1, c2) ->
                      Integer.compare(
                          c1.getRarityDetails().getPrintCount(),
                          c2.getRarityDetails().getPrintCount()))
              .collect(Collectors.toList());

      assertThat(sorted).hasSize(6);

      var printCounts = sorted.stream().map(c -> c.getRarityDetails().getPrintCount()).toList();
      assertThat(printCounts).containsExactly(50, 100, 150, 500, 1200, 25000);

      var rarest = sorted.get(0);
      assertThat(rarest.getTitle()).isEqualTo("Amazing Fantasy #15");
      assertThat(rarest.getRarityDetails().getPrintCount()).isEqualTo(50);
    }

    @Test
    @DisplayName("Sort by market value: older + rarer + higher grade first")
    void sortByMarketValue() {
      // Use existing getComics() and sort by market value (rarity first, then age, then grade)
      List<Comic> sorted =
          library.getComics().stream()
              .sorted(
                  (c1, c2) -> {
                    // Market value priority: rarity (print count) first, then age, then grade
                    // Lower print count = higher value
                    int rarityComparison =
                        Integer.compare(
                            c1.getRarityDetails().getPrintCount(),
                            c2.getRarityDetails().getPrintCount());
                    if (rarityComparison != 0) return rarityComparison;

                    // Older = higher value
                    int ageComparison =
                        c1.getRarityDetails().getYear().compareTo(c2.getRarityDetails().getYear());
                    if (ageComparison != 0) return ageComparison;

                    // Higher grade = higher value (reverse order)
                    return Double.compare(
                        c2.getRarityDetails().getGrade(), c1.getRarityDetails().getGrade());
                  })
              .collect(Collectors.toList());

      assertThat(sorted).hasSize(6);
      assertThat(sorted.get(0).getTitle())
          .isEqualTo("Amazing Fantasy #15"); // Lowest print count (50)
      assertThat(sorted.get(sorted.size() - 1).getTitle())
          .isEqualTo("The Dark Knight Returns TPB"); // Highest print count (25000)
    }

    @Test
    @DisplayName("Filter by max print count")
    void filterByRarityThreshold() {
      // Use existing getComics() and filter by print count using streams
      List<Comic> rare =
          library.getComics().stream()
              .filter(comic -> comic.getRarityDetails().getPrintCount() <= 500)
              .collect(Collectors.toList());

      assertThat(rare).hasSize(4);

      var titles = rare.stream().map(Comic::getTitle).collect(Collectors.toList());
      assertThat(titles)
          .containsExactlyInAnyOrder(
              "Amazing Fantasy #15",
              "Amazing Spider-Man #1 Variant",
              "The Incredible Hulk #1",
              "Batman #1");
    }

    @Test
    @DisplayName("Filter by minimum grade")
    void filterByMinimumGrade() {
      // Use existing getComics() and filter by grade using streams
      List<Comic> highGrade =
          library.getComics().stream()
              .filter(comic -> comic.getRarityDetails().getGrade() >= 9.0)
              .collect(Collectors.toList());

      assertThat(highGrade).hasSize(5);
      assertThat(highGrade.stream().map(Comic::getTitle).collect(Collectors.toList()))
          .doesNotContain("Batman #1");
    }

    @Test
    @DisplayName("Filter by vintage year")
    void filterByVintageYear() {
      // Use existing getComics() and filter by year using streams
      List<Comic> vintage =
          library.getComics().stream()
              .filter(comic -> comic.getRarityDetails().getYear().getValue() <= 1980)
              .collect(Collectors.toList());

      assertThat(vintage).hasSize(3);

      var titles = vintage.stream().map(Comic::getTitle).collect(Collectors.toList());
      assertThat(titles)
          .containsExactlyInAnyOrder("Amazing Fantasy #15", "The Incredible Hulk #1", "Batman #1");
    }

    @Test
    @DisplayName("Ultra-rare comics: print <= 100 and grade >= 9.5")
    void identifyUltraRareComics() {
      // Use existing getComics() and filter by print count and grade using streams
      List<Comic> ultra =
          library.getComics().stream()
              .filter(
                  comic ->
                      comic.getRarityDetails().getPrintCount() <= 100
                          && comic.getRarityDetails().getGrade() >= 9.5)
              .collect(Collectors.toList());

      assertThat(ultra).hasSize(2);

      var titles = ultra.stream().map(Comic::getTitle).collect(Collectors.toList());
      assertThat(titles)
          .containsExactlyInAnyOrder("Amazing Fantasy #15", "Amazing Spider-Man #1 Variant");
    }

    @Test
    @DisplayName("Comics should be clearly labeled with all rarity details")
    void verifyRarityDetailsLabeling() {
      // When - Check all comics have proper rarity details
      List<Comic> allComics = seed;

      // Then
      assertThat(allComics).isNotEmpty();

      // Verify each comic has complete rarity details
      allComics.forEach(
          comic -> {
            RarityDetails rarity = comic.getRarityDetails();

            // Verify rarity details object exists
            assertNotNull(rarity, "Comic should have rarity details");

            // Verify edition type is labeled
            assertNotNull(rarity.getEdition(), "Edition should be specified");
            assertThat(rarity.getEdition())
                .as("Edition should be a valid enum value")
                .isIn((Object[]) Edition.values());

            // Verify print count is labeled and reasonable
            assertThat(rarity.getPrintCount()).as("Print count should be positive").isPositive();

            // Verify grading is labeled and within valid range
            assertThat(rarity.getGrade())
                .as("Grade should be between 0.1 and 10.0")
                .isBetween(0.1, 10.0);

            // Verify year is labeled and reasonable
            assertNotNull(rarity.getYear(), "Year should be specified");
            assertThat(rarity.getYear().getValue())
                .as("Year should be reasonable")
                .isGreaterThan(1930)
                .isLessThanOrEqualTo(Year.now().getValue());
          });
    }
  }
}

// End of RareComicsDiscoveryIntegrationTest class
