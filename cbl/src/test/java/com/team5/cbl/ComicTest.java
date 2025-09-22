/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.team5.cbl;

import java.time.Year;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.team5.cbl.cbl_app.enums.CompanyName;
import com.team5.cbl.cbl_app.enums.Edition;
import com.team5.cbl.cbl_app.enums.Genre;
import com.team5.cbl.cbl_app.objects.Comic;
import com.team5.cbl.cbl_app.objects.Creator;
import com.team5.cbl.cbl_app.objects.Publisher;
import com.team5.cbl.cbl_app.objects.RarityDetails;

/**
 * @author vscode
 */
public class ComicTest {
  private Comic comic;

  @BeforeEach
  public void setUp() {
    List<Genre> genres = new ArrayList<>();
    Creator writer = new Creator("Test Creator", 45);
    var leadingCharacter =
        new com.team5.cbl.cbl_app.objects.Character("Test name", "This is a hero");
    var publisher = new Publisher(CompanyName.DC_COMICS);
    genres.add(Genre.ACTION);
    genres.add(Genre.ADVENTURE);
    comic =
        new Comic(
            "Test comic",
            genres,
            150,
            new RarityDetails(Edition.SINGLE_ISSUE, 150, 7.3, Year.now()),
            writer,
            leadingCharacter,
            publisher);
  }

  @Test
  public void getterTest() {
    List<Genre> expected = new ArrayList<>();
    RarityDetails expectedRarityDetails =
        new RarityDetails(Edition.SINGLE_ISSUE, 150, 7.3, Year.now());
    var expectedPublisher = new Publisher(CompanyName.DC_COMICS);
    var expectedLeadingCharacter =
        new com.team5.cbl.cbl_app.objects.Character("Test name", "This is a hero");

    expected.add(Genre.ACTION);
    expected.add(Genre.ADVENTURE);
    assertEquals(comic.getTitle(), "Test comic");
    assertEquals(comic.getGenre(), expected);
    assertEquals(comic.getRarityDetails(), expectedRarityDetails);
    assertEquals(comic.getNumberOfIssues(), 150);

    assertThat(comic.getLeadingCharacter()).isEqualTo(expectedLeadingCharacter);
    assertThat(comic.getPublisher()).isEqualTo(expectedPublisher);
  }

  @Test
  public void testSimpleConstructor() {
    // Test the simple constructor with all parameters
    Creator testWriter = new Creator("Simple Writer", 40);
    var testCharacter = new com.team5.cbl.cbl_app.objects.Character("Simple Hero", "A simple hero");
    RarityDetails testRarity = new RarityDetails(Edition.FIRST_EDITION, 1000, 9.5, Year.of(2023));

    Comic simpleComic = new Comic("Simple Comic", testWriter, testRarity, testCharacter);

    // Test that all provided parameters are set correctly
    assertEquals("Simple Comic", simpleComic.getTitle());
    assertEquals(testWriter, simpleComic.getHeadWriter());
    assertEquals(testRarity, simpleComic.getRarityDetails());
    assertEquals(testCharacter, simpleComic.getLeadingCharacter());

    // Test that creative team contains the head writer
    List<Creator> creativeTeam = simpleComic.getCreativeTeam();
    assertEquals(1, creativeTeam.size());
    assertEquals(testWriter, creativeTeam.get(0));

    // Test default values set by constructor
    assertEquals(new ArrayList<>(), simpleComic.getGenre()); // Empty genre list
    assertEquals(
        CompanyName.INDIE, simpleComic.getPublisher().getCompanyName()); // Default INDIE publisher
    assertEquals(Integer.valueOf(1), simpleComic.getNumberOfIssues()); // Default 1 issue
  }

  @Test
  public void testSimpleConstructor_WithNullWriter() {
    // Test the simple constructor with null head writer
    var testCharacter =
        new com.team5.cbl.cbl_app.objects.Character("Solo Hero", "A hero without a writer");
    RarityDetails testRarity = new RarityDetails(Edition.SECOND_EDITION, 500, 8.0, Year.of(2024));

    Comic comicWithNullWriter = new Comic("Comic Without Writer", null, testRarity, testCharacter);

    // Test that all provided parameters are set correctly
    assertEquals("Comic Without Writer", comicWithNullWriter.getTitle());
    assertEquals(null, comicWithNullWriter.getHeadWriter()); // No head writer
    assertEquals(testRarity, comicWithNullWriter.getRarityDetails());
    assertEquals(testCharacter, comicWithNullWriter.getLeadingCharacter());

    // Test that creative team is empty when null writer is provided
    List<Creator> creativeTeam = comicWithNullWriter.getCreativeTeam();
    assertEquals(0, creativeTeam.size());
    assertTrue(creativeTeam.isEmpty());

    // Test default values are still set correctly
    assertEquals(new ArrayList<>(), comicWithNullWriter.getGenre());
    assertEquals(CompanyName.INDIE, comicWithNullWriter.getPublisher().getCompanyName());
    assertEquals(Integer.valueOf(1), comicWithNullWriter.getNumberOfIssues());
  }

  @Test
  public void testSimpleConstructor_DefaultPublisherObject() {
    // Test that the default publisher is a proper Publisher object, not just the enum
    Creator testWriter = new Creator("Indie Writer", 35);
    var testCharacter =
        new com.team5.cbl.cbl_app.objects.Character("Indie Hero", "An independent hero");
    RarityDetails testRarity = new RarityDetails(Edition.TRADE_PAPERBACK, 2000, 7.5, Year.of(2022));

    Comic indieComic = new Comic("Indie Comic", testWriter, testRarity, testCharacter);

    // Test that publisher is a complete Publisher object with INDIE company name
    Publisher publisher = indieComic.getPublisher();
    assertThat(publisher).isNotNull();
    assertEquals(CompanyName.INDIE, publisher.getCompanyName());

    // Verify it's a proper Publisher instance, not just an enum value
    assertThat(publisher).isInstanceOf(Publisher.class);
  }

  @Test
  public void testSimpleConstructor_CompareWithFullConstructor() {
    // Create comics using both constructors and compare behavior
    Creator writer = new Creator("Comparison Writer", 45);
    var character =
        new com.team5.cbl.cbl_app.objects.Character("Comparison Hero", "A hero for comparison");
    RarityDetails rarity = new RarityDetails(Edition.SINGLE_ISSUE, 100, 8.5, Year.now());

    // Simple constructor
    Comic simpleComic = new Comic("Comparison Comic", writer, rarity, character);

    // Full constructor with equivalent parameters
    List<Genre> emptyGenres = new ArrayList<>();
    Publisher indiePublisher = new Publisher(CompanyName.INDIE);
    Comic fullComic =
        new Comic("Comparison Comic", emptyGenres, 1, rarity, writer, character, indiePublisher);

    // Compare that they produce equivalent results
    assertEquals(simpleComic.getTitle(), fullComic.getTitle());
    assertEquals(simpleComic.getHeadWriter(), fullComic.getHeadWriter());
    assertEquals(simpleComic.getRarityDetails(), fullComic.getRarityDetails());
    assertEquals(simpleComic.getLeadingCharacter(), fullComic.getLeadingCharacter());
    assertEquals(simpleComic.getGenre(), fullComic.getGenre());
    assertEquals(
        simpleComic.getPublisher().getCompanyName(), fullComic.getPublisher().getCompanyName());
    assertEquals(simpleComic.getNumberOfIssues(), fullComic.getNumberOfIssues());
    assertEquals(simpleComic.getCreativeTeam(), fullComic.getCreativeTeam());
  }

  @Test
  public void testEquals_SameObject() {
    // Test reflexive property: object equals itself
    assertTrue(comic.equals(comic));
  }

  @Test
  public void testEquals_IdenticalComics() {
    // Create another comic with identical properties
    List<Genre> genres = new ArrayList<>();
    genres.add(Genre.ACTION);
    genres.add(Genre.ADVENTURE);
    Creator writer = new Creator("Test Creator", 45);
    var leadingCharacter =
        new com.team5.cbl.cbl_app.objects.Character("Test name", "This is a hero");
    var publisher = new Publisher(CompanyName.DC_COMICS);

    Comic identicalComic =
        new Comic(
            "Test comic",
            genres,
            150,
            new RarityDetails(Edition.SINGLE_ISSUE, 150, 7.3, Year.now()),
            writer,
            leadingCharacter,
            publisher);

    // Test symmetric property: if a.equals(b) then b.equals(a)
    assertTrue(comic.equals(identicalComic));
    assertTrue(identicalComic.equals(comic));
  }

  @Test
  public void testEquals_DifferentTitle() {
    List<Genre> genres = new ArrayList<>();
    genres.add(Genre.ACTION);
    genres.add(Genre.ADVENTURE);
    Creator writer = new Creator("Test Creator", 45);
    var leadingCharacter =
        new com.team5.cbl.cbl_app.objects.Character("Test name", "This is a hero");
    var publisher = new Publisher(CompanyName.DC_COMICS);

    Comic differentTitleComic =
        new Comic(
            "Different comic", // Different title
            genres,
            150,
            new RarityDetails(Edition.SINGLE_ISSUE, 150, 7.3, Year.now()),
            writer,
            leadingCharacter,
            publisher);

    assertFalse(comic.equals(differentTitleComic));
  }

  @Test
  public void testEquals_DifferentGenres() {
    List<Genre> differentGenres = new ArrayList<>();
    differentGenres.add(Genre.HORROR); // Different genre
    Creator writer = new Creator("Test Creator", 45);
    var leadingCharacter =
        new com.team5.cbl.cbl_app.objects.Character("Test name", "This is a hero");
    var publisher = new Publisher(CompanyName.DC_COMICS);

    Comic differentGenreComic =
        new Comic(
            "Test comic",
            differentGenres,
            150,
            new RarityDetails(Edition.SINGLE_ISSUE, 150, 7.3, Year.now()),
            writer,
            leadingCharacter,
            publisher);

    assertFalse(comic.equals(differentGenreComic));
  }

  @Test
  public void testEquals_DifferentCreativeTeam() {
    List<Genre> genres = new ArrayList<>();
    genres.add(Genre.ACTION);
    genres.add(Genre.ADVENTURE);
    Creator differentWriter = new Creator("Different Creator", 50); // Different creator
    var leadingCharacter =
        new com.team5.cbl.cbl_app.objects.Character("Test name", "This is a hero");
    var publisher = new Publisher(CompanyName.DC_COMICS);

    Comic differentCreatorComic =
        new Comic(
            "Test comic",
            genres,
            150,
            new RarityDetails(Edition.SINGLE_ISSUE, 150, 7.3, Year.now()),
            differentWriter,
            leadingCharacter,
            publisher);

    assertFalse(comic.equals(differentCreatorComic));
  }

  @Test
  public void testEquals_DifferentNumberOfIssues() {
    // Test equals with different number of issues
    List<Genre> genres = new ArrayList<>();
    genres.add(Genre.ACTION);
    genres.add(Genre.ADVENTURE);
    Creator writer = new Creator("Test Creator", 45);
    var leadingCharacter =
        new com.team5.cbl.cbl_app.objects.Character("Test name", "This is a hero");
    var publisher = new Publisher(CompanyName.DC_COMICS);

    Comic differentIssuesComic =
        new Comic(
            "Test comic",
            genres,
            200, // Different number of issues
            new RarityDetails(Edition.SINGLE_ISSUE, 150, 7.3, Year.now()),
            writer,
            leadingCharacter,
            publisher);

    assertFalse(comic.equals(differentIssuesComic));
  }

  @Test
  public void testEquals_DifferentRarityDetails() {
    // Test equals with different rarity details
    List<Genre> genres = new ArrayList<>();
    genres.add(Genre.ACTION);
    genres.add(Genre.ADVENTURE);
    Creator writer = new Creator("Test Creator", 45);
    var leadingCharacter =
        new com.team5.cbl.cbl_app.objects.Character("Test name", "This is a hero");
    var publisher = new Publisher(CompanyName.DC_COMICS);

    Comic differentRarityComic =
        new Comic(
            "Test comic",
            genres,
            150,
            new RarityDetails(Edition.FIRST_EDITION, 100, 9.0, Year.of(2020)), // Different rarity
            writer,
            leadingCharacter,
            publisher);

    assertFalse(comic.equals(differentRarityComic));
  }

  @Test
  public void testEquals_DifferentLeadingCharacter() {
    // Test equals with different leading character
    List<Genre> genres = new ArrayList<>();
    genres.add(Genre.ACTION);
    genres.add(Genre.ADVENTURE);
    Creator writer = new Creator("Test Creator", 45);
    var differentCharacter =
        new com.team5.cbl.cbl_app.objects.Character("Different Hero", "This is a different hero");
    var publisher = new Publisher(CompanyName.DC_COMICS);

    Comic differentCharacterComic =
        new Comic(
            "Test comic",
            genres,
            150,
            new RarityDetails(Edition.SINGLE_ISSUE, 150, 7.3, Year.now()),
            writer,
            differentCharacter,
            publisher);

    assertFalse(comic.equals(differentCharacterComic));
  }

  @Test
  public void testEquals_DifferentPublisher() {
    // Test equals with different publisher
    List<Genre> genres = new ArrayList<>();
    genres.add(Genre.ACTION);
    genres.add(Genre.ADVENTURE);
    Creator writer = new Creator("Test Creator", 45);
    var leadingCharacter =
        new com.team5.cbl.cbl_app.objects.Character("Test name", "This is a hero");
    var differentPublisher = new Publisher(CompanyName.MARVEL_COMICS);

    Comic differentPublisherComic =
        new Comic(
            "Test comic",
            genres,
            150,
            new RarityDetails(Edition.SINGLE_ISSUE, 150, 7.3, Year.now()),
            writer,
            leadingCharacter,
            differentPublisher);

    assertFalse(comic.equals(differentPublisherComic));
  }

  @Test
  public void testEquals_Null() {
    // Test with null
    assertFalse(comic.equals(null));
  }

  @Test
  public void testEquals_DifferentClass() {
    // Test with different class
    String notAComic = "Not a comic";
    assertFalse(comic.equals(notAComic));
  }

  @Test
  public void testHashCode_IdenticalComics() {
    // Create another comic with identical properties
    List<Genre> genres = new ArrayList<>();
    genres.add(Genre.ACTION);
    genres.add(Genre.ADVENTURE);
    Creator writer = new Creator("Test Creator", 45);
    var leadingCharacter =
        new com.team5.cbl.cbl_app.objects.Character("Test name", "This is a hero");
    var publisher = new Publisher(CompanyName.DC_COMICS);

    Comic identicalComic =
        new Comic(
            "Test comic",
            genres,
            150,
            new RarityDetails(Edition.SINGLE_ISSUE, 150, 7.3, Year.now()),
            writer,
            leadingCharacter,
            publisher);

    // Test hashCode contract: if two objects are equal, they must have the same hash code
    assertEquals(comic.hashCode(), identicalComic.hashCode());
  }

  @Test
  public void testHashCode_DifferentComics() {
    List<Genre> differentGenres = new ArrayList<>();
    differentGenres.add(Genre.HORROR);
    Creator writer = new Creator("Test Creator", 45);
    var leadingCharacter =
        new com.team5.cbl.cbl_app.objects.Character("Test name", "This is a hero");
    var publisher = new Publisher(CompanyName.DC_COMICS);

    Comic differentComic =
        new Comic(
            "Different comic",
            differentGenres,
            200,
            new RarityDetails(Edition.FIRST_EDITION, 100, 9.0, Year.of(2020)),
            writer,
            leadingCharacter,
            publisher);

    // Different objects should ideally have different hash codes (though not guaranteed)
    assertNotEquals(comic.hashCode(), differentComic.hashCode());
  }

  @Test
  public void testHashCode_Consistency() {
    // Test that hashCode is consistent: multiple calls should return the same value
    int firstCall = comic.hashCode();
    int secondCall = comic.hashCode();
    int thirdCall = comic.hashCode();

    assertEquals(firstCall, secondCall);
    assertEquals(secondCall, thirdCall);
  }

  @Test
  public void testEquals_Transitive() {
    // Test transitive property: if a.equals(b) and b.equals(c), then a.equals(c)
    List<Genre> genres1 = new ArrayList<>();
    genres1.add(Genre.ACTION);
    genres1.add(Genre.ADVENTURE);

    List<Genre> genres2 = new ArrayList<>();
    genres2.add(Genre.ACTION);
    genres2.add(Genre.ADVENTURE);

    List<Genre> genres3 = new ArrayList<>();
    genres3.add(Genre.ACTION);
    genres3.add(Genre.ADVENTURE);

    Creator writer1 = new Creator("Test Creator", 45);
    Creator writer2 = new Creator("Test Creator", 45);
    Creator writer3 = new Creator("Test Creator", 45);

    var character1 = new com.team5.cbl.cbl_app.objects.Character("Test name", "This is a hero");
    var character2 = new com.team5.cbl.cbl_app.objects.Character("Test name", "This is a hero");
    var character3 = new com.team5.cbl.cbl_app.objects.Character("Test name", "This is a hero");

    var publisher1 = new Publisher(CompanyName.DC_COMICS);
    var publisher2 = new Publisher(CompanyName.DC_COMICS);
    var publisher3 = new Publisher(CompanyName.DC_COMICS);

    Comic comic1 =
        new Comic(
            "Test comic",
            genres1,
            150,
            new RarityDetails(Edition.SINGLE_ISSUE, 150, 7.3, Year.now()),
            writer1,
            character1,
            publisher1);

    Comic comic2 =
        new Comic(
            "Test comic",
            genres2,
            150,
            new RarityDetails(Edition.SINGLE_ISSUE, 150, 7.3, Year.now()),
            writer2,
            character2,
            publisher2);

    Comic comic3 =
        new Comic(
            "Test comic",
            genres3,
            150,
            new RarityDetails(Edition.SINGLE_ISSUE, 150, 7.3, Year.now()),
            writer3,
            character3,
            publisher3);

    // Test transitive property
    assertTrue(comic1.equals(comic2));
    assertTrue(comic2.equals(comic3));
    assertTrue(comic1.equals(comic3));
  }

  @Test
  public void testToString() {
    // Test that toString returns a non-null, non-empty string
    String toStringResult = comic.toString();

    assertThat(toStringResult).isNotNull();
    assertThat(toStringResult).isNotEmpty();

    // Test that toString contains key comic information
    assertThat(toStringResult).contains("Test comic"); // Title
    assertThat(toStringResult).contains("[ACTION, ADVENTURE]"); // Genres
    assertThat(toStringResult).contains("150"); // Number of issues

    // Test that toString is consistent - multiple calls should return the same result
    String secondCall = comic.toString();
    assertEquals(toStringResult, secondCall);

    // Test expected format (based on the Comic class toString implementation)
    String expectedPattern =
        "Comic\\{.*title='Test comic'.*genre=\\[ACTION, ADVENTURE\\].*numberOfIssues=150.*\\}";
    assertThat(toStringResult).matches(expectedPattern);
  }

  @Test
  public void testToString_DifferentComics() {
    // Create a different comic to test that toString produces different outputs
    List<Genre> differentGenres = new ArrayList<>();
    differentGenres.add(Genre.HORROR);
    Creator differentWriter = new Creator("Different Creator", 50);
    var differentCharacter =
        new com.team5.cbl.cbl_app.objects.Character("Different Hero", "Different bio");
    var differentPublisher = new Publisher(CompanyName.MARVEL_COMICS);

    Comic differentComic =
        new Comic(
            "Different Comic Title",
            differentGenres,
            25,
            new RarityDetails(Edition.FIRST_EDITION, 100, 9.0, Year.of(2020)),
            differentWriter,
            differentCharacter,
            differentPublisher);

    String originalToString = comic.toString();
    String differentToString = differentComic.toString();

    // Verify that different comics produce different string representations
    assertNotEquals(originalToString, differentToString);

    // Verify the different comic's toString contains its specific information
    assertThat(differentToString).contains("Different Comic Title");
    assertThat(differentToString).contains("[HORROR]");
    assertThat(differentToString).contains("25");
  }

  @Test
  public void testToString_WithNullValues() {
    // Test toString behavior with null values (if your Comic allows them)
    List<Genre> genres = new ArrayList<>();
    genres.add(Genre.ACTION);
    Creator writer = new Creator("Test Creator", 45);
    var publisher = new Publisher(CompanyName.DC_COMICS);

    // Create comic with null leading character
    Comic comicWithNullCharacter =
        new Comic(
            "Test comic with null character",
            genres,
            100,
            new RarityDetails(Edition.SINGLE_ISSUE, 100, 8.0, Year.now()),
            writer,
            null, // null leading character
            publisher);

    String toStringResult = comicWithNullCharacter.toString();

    // Verify toString handles null values gracefully
    assertThat(toStringResult).isNotNull();
    assertThat(toStringResult).isNotEmpty();
    assertThat(toStringResult).contains("Test comic with null character");
    assertThat(toStringResult).contains("leadingCharacter=null");
  }
}
