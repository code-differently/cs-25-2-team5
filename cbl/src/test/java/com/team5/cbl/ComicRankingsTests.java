package com.team5.cbl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.team5.cbl.cbl_app.objects.Character;
import com.team5.cbl.cbl_app.objects.Comic;
import com.team5.cbl.cbl_app.objects.Creator;
import com.team5.cbl.cbl_app.objects.Publisher;
import com.team5.cbl.cbl_app.objects.RarityDetails;

public class ComicRankingsTests {

  private ComicRankings comicRankings;
  private List<Comic> testComics;

  @BeforeEach
  @SuppressWarnings("unused")
  void setUp() {

    Creator stanLee = new Creator("Stan Lee", 95);
    Creator alanMoore = new Creator("Alan Moore", 70);
    Creator frankMiller = new Creator("Frank Miller", 67);

    Character spiderMan = new Character("Spider-Man", "Friendly neighborhood superhero");
    Character batman = new Character("Batman", "Dark knight of Gotham");
    Character daredevil = new Character("Daredevil", "Man without fear");

    // Fix Publisher constructor calls
    Publisher marvel = new Publisher("Marvel Comics");
    Publisher dc = new Publisher("DC Comics");

    // Create comics with different grades
    RarityDetails mint = new RarityDetails(null, 0, 9.8, null); // High grade
    RarityDetails fine = new RarityDetails(null, 0, 6.0, null); // Medium grade
    RarityDetails poor = new RarityDetails(null, 0, 1.0, null); // Low grade
    RarityDetails nearMint = new RarityDetails(null, 0, 9.2, null); // High grade
    RarityDetails veryFine = new RarityDetails(null, 0, 8.0, null); // Medium-high grade

    testComics =
        Arrays.asList(
            new Comic("Amazing Spider-Man #1", stanLee, fine, spiderMan), // Grade 6.0
            new Comic("Batman #1", frankMiller, mint, batman), // Grade 9.8
            new Comic("Daredevil #1", frankMiller, poor, daredevil), // Grade 1.0
            new Comic("X-Men #1", stanLee, nearMint, spiderMan), // Grade 9.2
            new Comic("Watchmen #1", alanMoore, veryFine, batman) // Grade 8.0
            );

    comicRankings = new ComicRankings(testComics);
  }

  @Test
  void testGetRankings_SortsByGradeDescending() {
    // When
    List<Comic> rankings = comicRankings.getRankings();

    // Then
    assertNotNull(rankings);
    assertEquals(5, rankings.size());

    // Verify they're sorted by grade (highest first)
    assertEquals(9.8, rankings.get(0).getRarityDetails().getGrade());
    assertEquals(9.2, rankings.get(1).getRarityDetails().getGrade());
    assertEquals(8.0, rankings.get(2).getRarityDetails().getGrade());
    assertEquals(6.0, rankings.get(3).getRarityDetails().getGrade());
    assertEquals(1.0, rankings.get(4).getRarityDetails().getGrade());
  }

  @Test
  void testGetRankings_CorrectComicOrder() {
    // When
    List<Comic> rankings = comicRankings.getRankings();

    // Then - verify specific comics are in correct positions
    assertEquals("Batman #1", rankings.get(0).getTitle()); // Highest grade (9.8)
    assertEquals("X-Men #1", rankings.get(1).getTitle()); // Second highest (9.2)
    assertEquals("Watchmen #1", rankings.get(2).getTitle()); // Third (8.0)
    assertEquals("Amazing Spider-Man #1", rankings.get(3).getTitle()); // Fourth (6.0)
    assertEquals("Daredevil #1", rankings.get(4).getTitle()); // Lowest (1.0)
  }

  @Test
  void testGetRankings_WithEmptyList() {
    // Given
    ComicRankings emptyRankings = new ComicRankings(new ArrayList<>());

    // When
    List<Comic> rankings = emptyRankings.getRankings();

    // Then
    assertNotNull(rankings);
    assertTrue(rankings.isEmpty());
  }

  @Test
  void testGetRankings_WithNullList() {
    // Given
    ComicRankings nullRankings = new ComicRankings(null);

    // When
    List<Comic> rankings = nullRankings.getRankings();

    // Then
    assertNotNull(rankings);
    assertTrue(rankings.isEmpty());
  }

  @Test
  void testGetRankings_WithSingleComic() {
    // Given
    Creator creator = new Creator("Test Creator", 50);
    Character character = new Character("Test Character", "Test bio");
    RarityDetails rarity = new RarityDetails(null, 0, 7.5, null);
    Comic singleComic = new Comic("Test Comic", creator, rarity, character);

    ComicRankings singleRankings = new ComicRankings(Arrays.asList(singleComic));

    // When
    List<Comic> rankings = singleRankings.getRankings();

    // Then
    assertNotNull(rankings);
    assertEquals(1, rankings.size());
    assertEquals("Test Comic", rankings.get(0).getTitle());
    assertEquals(7.5, rankings.get(0).getRarityDetails().getGrade());
  }

  @Test
  void testGetRankings_WithSameGrades() {
    // Given - comics with identical grades
    Creator creator = new Creator("Test Creator", 50);
    Character character = new Character("Test Character", "Test bio");
    RarityDetails sameGrade = new RarityDetails(null, 0, 8.5, null);

    List<Comic> sameGradeComics =
        Arrays.asList(
            new Comic("Comic A", creator, sameGrade, character),
            new Comic("Comic B", creator, sameGrade, character),
            new Comic("Comic C", creator, sameGrade, character));

    ComicRankings sameGradeRankings = new ComicRankings(sameGradeComics);

    // When
    List<Comic> rankings = sameGradeRankings.getRankings();

    // Then
    assertNotNull(rankings);
    assertEquals(3, rankings.size());
    // All should have the same grade
    rankings.forEach(comic -> assertEquals(8.5, comic.getRarityDetails().getGrade()));
  }

  @Test
  void testGetRankings_DoesNotModifyOriginalList() {
    // Given
    int originalSize = testComics.size();
    String originalFirstTitle = testComics.get(0).getTitle();

    // When
    List<Comic> rankings = comicRankings.getRankings();

    // Then - original list should be unchanged
    assertEquals(originalSize, testComics.size());
    assertEquals(originalFirstTitle, testComics.get(0).getTitle());

    // But rankings should be different order
    assertNotEquals(testComics.get(0).getTitle(), rankings.get(0).getTitle());
  }
}
