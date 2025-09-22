package com.team5.cbl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

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
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * Integration test for User Story 3: As a writer, I want to add new comics to the library so that I
 * can showcase my stories and share my work with readers.
 *
 * <p>Tests comic addition using the existing ComicLibrary.addComic(Comic) method and verifies
 * lookup capability after addition.
 */
public class AddComicIntegrationTest {

  private ComicLibrary library;
  private List<Comic> initialSeed;
  private int initialLibrarySize;

  @BeforeEach
  void setUp() {
    initialSeed = new ArrayList<>();

    // Add some existing comics to test against
    List<Genre> superhero = List.of(Genre.SUPERHERO);
    Creator existingCreator = new Creator("Stan Lee", 95);
    Character existingCharacter = new Character("Spider-Man", "Friendly neighborhood hero");
    Publisher marvel = new Publisher(CompanyName.DC_COMICS); // Using existing enum value

    Comic existingComic =
        new Comic(
            "Amazing Spider-Man #1",
            superhero,
            1000,
            new RarityDetails(Edition.SINGLE_ISSUE, 1000, 9.0, Year.of(1963)),
            existingCreator,
            existingCharacter,
            marvel);

    initialSeed.add(existingComic);
    library = new ComicLibrary(initialSeed);
    initialLibrarySize = initialSeed.size();
  }

  @Test
  @DisplayName("Should successfully add a new comic with all required fields")
  void addNewComic_WithAllRequiredFields_ShouldSucceed() {
    // Given
    String title = "My Amazing Story #1";
    Creator author = new Creator("Jane Writer", 35);
    Character mainCharacter = new Character("Hero X", "A brave new hero");
    Publisher publisher = new Publisher(CompanyName.DC_COMICS);
    List<Genre> genres = List.of(Genre.SUPERHERO, Genre.ACTION);
    RarityDetails rarity = new RarityDetails(Edition.SINGLE_ISSUE, 6, 9.5, Year.of(2024));

    Comic newComic = new Comic(title, genres, 100, rarity, author, mainCharacter, publisher);

    // When
    library.addComic(newComic);

    // Then - Verify the comic was actually added to the library by checking we can find it
    List<Comic> foundComics = library.filterByComicTitle(title);
    assertThat(foundComics).hasSize(1);

    Comic foundComic = foundComics.get(0);
    assertEquals(title, foundComic.getTitle());
    assertEquals(author.getName(), foundComic.getHeadWriter().getName());
    assertEquals(publisher.getCompanyName(), foundComic.getPublisher().getCompanyName());
    assertEquals(Year.of(2024), foundComic.getRarityDetails().getYear());
    assertEquals(9.5, foundComic.getRarityDetails().getGrade(), 1e-9);
  }

  @Test
  @DisplayName("Should be able to look up newly added comic by title")
  void lookupNewlyAddedComic_ByTitle_ShouldBeFound() {
    // Given - Add a new comic first
    String title = "My Indie Comic Series #1";
    Creator author = new Creator("Independent Writer", 28);
    Publisher publisher = new Publisher(CompanyName.INDIE);
    List<Genre> genres = List.of(Genre.SCI_FI);
    Character mainCharacter = new Character("Protagonist", "Main character of the story");
    RarityDetails rarity = new RarityDetails(Edition.SINGLE_ISSUE, 4, 9.0, Year.of(2024));

    Comic newComic = new Comic(title, genres, 150, rarity, author, mainCharacter, publisher);
    library.addComic(newComic);

    // When - Look up the comic by title
    List<Comic> foundComics = library.filterByComicTitle(title);

    // Then
    assertThat(foundComics).hasSize(1);
    Comic foundComic = foundComics.get(0);

    // Verify all the details match what was added
    assertEquals(title, foundComic.getTitle());
    assertEquals(author.getName(), foundComic.getHeadWriter().getName());
    assertEquals(publisher.getCompanyName(), foundComic.getPublisher().getCompanyName());
    assertEquals(Year.of(2024), foundComic.getRarityDetails().getYear());
    assertEquals(4, foundComic.getRarityDetails().getPrintCount());
  }

  @Test
  @DisplayName("Should be able to look up newly added comic by author")
  void lookupNewlyAddedComic_ByAuthor_ShouldBeFound() {
    // Given - Add a new comic first
    String authorName = "Sarah Storyteller";
    Creator author = new Creator(authorName, 42);
    String title = "Epic Fantasy Adventures #1";
    Publisher publisher = new Publisher(CompanyName.INDIE);
    List<Genre> genres = List.of(Genre.FANTASY);
    Character mainCharacter = new Character("Wizard", "Powerful spellcaster");
    RarityDetails rarity = new RarityDetails(Edition.TRADE_PAPERBACK, 12, 8.5, Year.of(2024));

    Comic newComic = new Comic(title, genres, 100, rarity, author, mainCharacter, publisher);
    library.addComic(newComic);

    // When - Look up the comic by author
    List<Comic> foundComics = library.filterByCreator(authorName);

    // Then
    assertThat(foundComics).hasSize(1);
    Comic foundComic = foundComics.get(0);
    assertEquals(title, foundComic.getTitle());
    assertEquals(authorName, foundComic.getHeadWriter().getName());
  }

  @Test
  @DisplayName("Should allow adding multiple comics by the same author")
  void addMultipleComicsBySameAuthor_ShouldSucceed() {
    // Given
    Creator author = new Creator("Prolific Writer", 45);
    Publisher publisher = new Publisher(CompanyName.INDIE);
    List<Genre> genres = List.of(Genre.ACTION);

    Comic comic1 =
        new Comic(
            "Series Vol 1",
            genres,
            100,
            new RarityDetails(Edition.SINGLE_ISSUE, 5, 9.0, Year.of(2024)),
            author,
            new Character("Hero A", "First hero"),
            publisher);

    Comic comic2 =
        new Comic(
            "Series Vol 2",
            genres,
            100,
            new RarityDetails(Edition.SINGLE_ISSUE, 5, 9.0, Year.of(2024)),
            author,
            new Character("Hero B", "Second hero"),
            publisher);

    // When - Add both comics
    library.addComic(comic1);
    library.addComic(comic2);

    // Then - Both comics should be found when searching by author
    List<Comic> foundComics = library.filterByCreator("Prolific Writer");
    assertThat(foundComics).hasSize(2);

    var titles = foundComics.stream().map(Comic::getTitle).toList();
    assertThat(titles).containsExactlyInAnyOrder("Series Vol 1", "Series Vol 2");
  }

  @Test
  @DisplayName("Should allow adding comics with same title but different authors")
  void addComic_SameTitleDifferentAuthor_ShouldSucceed() {
    // Given
    String title = "Popular Comic Title";
    Creator author1 = new Creator("First Author", 35);
    Creator author2 = new Creator("Second Author", 40);
    Publisher publisher = new Publisher(CompanyName.INDIE);
    List<Genre> genres = List.of(Genre.ACTION);
    Character mainCharacter = new Character("Character", "Main character");
    RarityDetails rarity = new RarityDetails(Edition.SINGLE_ISSUE, 5, 9.0, Year.of(2024));

    Comic comic1 = new Comic(title, genres, 150, rarity, author1, mainCharacter, publisher);
    Comic comic2 = new Comic(title, genres, 120, rarity, author2, mainCharacter, publisher);

    // When - Add both comics with same title but different authors
    library.addComic(comic1);
    library.addComic(comic2);

    // Then - Both comics should be found
    List<Comic> foundComics = library.filterByComicTitle(title);
    assertThat(foundComics).hasSize(2);

    var authorNames = foundComics.stream().map(comic -> comic.getHeadWriter().getName()).toList();
    assertThat(authorNames).containsExactlyInAnyOrder("First Author", "Second Author");
  }

  @Test
  @DisplayName("Should handle adding comics with current year successfully")
  void addComic_CurrentYear_ShouldSucceed() {
    // Given
    String title = "Current Year Comic";
    Creator author = new Creator("Current Writer", 35);
    Publisher publisher = new Publisher(CompanyName.INDIE);
    List<Genre> genres = List.of(Genre.SUPERHERO);
    Character mainCharacter = new Character("Modern Hero", "Contemporary character");
    RarityDetails rarity = new RarityDetails(Edition.SINGLE_ISSUE, 6, 8.5, Year.now());

    Comic newComic = new Comic(title, genres, 100, rarity, author, mainCharacter, publisher);

    // When
    library.addComic(newComic);

    // Then - Verify it can be looked up
    List<Comic> foundComics = library.filterByComicTitle(title);
    assertThat(foundComics).hasSize(1);
    assertEquals(Year.now(), foundComics.get(0).getRarityDetails().getYear());
  }

  @Test
  @DisplayName("Should handle adding vintage comics")
  void addComic_VintageYear_ShouldSucceed() {
    // Given
    String title = "Vintage Comic Classic";
    Creator author = new Creator("Vintage Writer", 85);
    Publisher publisher = new Publisher(CompanyName.DC_COMICS);
    List<Genre> genres = List.of(Genre.SUPERHERO);
    Character mainCharacter = new Character("Classic Hero", "Vintage character");
    RarityDetails rarity = new RarityDetails(Edition.SINGLE_ISSUE, 100, 7.5, Year.of(1965));

    Comic vintageComic = new Comic(title, genres, 100, rarity, author, mainCharacter, publisher);

    // When
    library.addComic(vintageComic);

    // Then
    List<Comic> foundComics = library.filterByComicTitle(title);
    assertThat(foundComics).hasSize(1);
    assertEquals(Year.of(1965), foundComics.get(0).getRarityDetails().getYear());
  }

  @Test
  @DisplayName("Should verify all required comic attributes are present after addition")
  void addedComic_ShouldContainAllRequiredFields() {
    // Given
    String title = "Complete Comic Test";
    Creator author = new Creator("Detail Writer", 33);
    Publisher publisher = new Publisher(CompanyName.DC_COMICS);
    List<Genre> genres = List.of(Genre.HORROR);
    Character mainCharacter = new Character("Detective", "Solving mysteries");
    RarityDetails rarity = new RarityDetails(Edition.TRADE_PAPERBACK, 8, 9.2, Year.of(2024));

    Comic newComic = new Comic(title, genres, 100, rarity, author, mainCharacter, publisher);

    // When
    library.addComic(newComic);

    // Then - Find and verify all fields
    List<Comic> foundComics = library.filterByComicTitle(title);
    assertThat(foundComics).hasSize(1);

    Comic foundComic = foundComics.get(0);

    // Verify title
    assertNotNull(foundComic.getTitle());
    assertEquals(title, foundComic.getTitle());

    // Verify author
    assertNotNull(foundComic.getHeadWriter());
    assertEquals(author.getName(), foundComic.getHeadWriter().getName());

    // Verify publisher
    assertNotNull(foundComic.getPublisher());
    assertEquals(publisher.getCompanyName(), foundComic.getPublisher().getCompanyName());

    // Verify date
    assertNotNull(foundComic.getRarityDetails());
    assertEquals(Year.of(2024), foundComic.getRarityDetails().getYear());

    // Verify number of issues (stored as print count in rarity details)
    assertEquals(8, foundComic.getRarityDetails().getPrintCount());

    // Verify grade
    assertEquals(9.2, foundComic.getRarityDetails().getGrade(), 1e-9);

    // Verify character
    assertNotNull(foundComic.getLeadingCharacter());
    assertEquals(mainCharacter.getHeroName(), foundComic.getLeadingCharacter().getHeroName());

    // Verify genres
    assertNotNull(foundComic.getGenre());
    assertThat(foundComic.getGenre()).contains(Genre.HORROR);
  }

  @Test
  @DisplayName("Should maintain library consistency after adding multiple comics")
  void addMultipleComics_ShouldMaintainConsistency() {
    // Given - Create multiple different comics
    Publisher publisher = new Publisher(CompanyName.INDIE);
    List<Genre> genres = List.of(Genre.SCI_FI);

    Comic comic1 =
        new Comic(
            "Comic One",
            genres,
            100,
            new RarityDetails(Edition.SINGLE_ISSUE, 3, 8.0, Year.of(2024)),
            new Creator("Author A", 30),
            new Character("Character A", "First character"),
            publisher);

    Comic comic2 =
        new Comic(
            "Comic Two",
            genres,
            100,
            new RarityDetails(Edition.SINGLE_ISSUE, 4, 8.5, Year.of(2024)),
            new Creator("Author B", 35),
            new Character("Character B", "Second character"),
            publisher);

    Comic comic3 =
        new Comic(
            "Comic Three",
            genres,
            100,
            new RarityDetails(Edition.SINGLE_ISSUE, 5, 9.0, Year.of(2024)),
            new Creator("Author C", 40),
            new Character("Character C", "Third character"),
            publisher);

    // When - Add all comics
    library.addComic(comic1);
    library.addComic(comic2);
    library.addComic(comic3);

    // Then - All comics should be searchable individually
    assertThat(library.filterByComicTitle("Comic One")).hasSize(1);
    assertThat(library.filterByComicTitle("Comic Two")).hasSize(1);
    assertThat(library.filterByComicTitle("Comic Three")).hasSize(1);

    // And all should be findable by their respective authors
    assertThat(library.filterByCreator("Author A")).hasSize(1);
    assertThat(library.filterByCreator("Author B")).hasSize(1);
    assertThat(library.filterByCreator("Author C")).hasSize(1);

    // And all should be findable by their characters
    assertThat(library.filterByCharacter("Character A")).hasSize(1);
    assertThat(library.filterByCharacter("Character B")).hasSize(1);
    assertThat(library.filterByCharacter("Character C")).hasSize(1);
  }

  @Test
  @DisplayName("Should handle different edition types correctly")
  void addComicsWithDifferentEditions_ShouldSucceed() {
    // Given - Comics with different edition types
    Creator author = new Creator("Edition Tester", 40);
    Publisher publisher = new Publisher(CompanyName.DC_COMICS);
    List<Genre> genres = List.of(Genre.ACTION);
    Character character = new Character("Universal Hero", "Works in any edition");

    Comic singleIssue =
        new Comic(
            "Single Issue Comic",
            genres,
            100,
            new RarityDetails(Edition.SINGLE_ISSUE, 1, 9.0, Year.of(2024)),
            author,
            character,
            publisher);

    Comic tradePaperback =
        new Comic(
            "Trade Paperback Comic",
            genres,
            100,
            new RarityDetails(Edition.TRADE_PAPERBACK, 200, 8.5, Year.of(2024)),
            author,
            character,
            publisher);

    // When - Add both comics
    library.addComic(singleIssue);
    library.addComic(tradePaperback);

    // Then - Both should be findable and have correct edition types
    List<Comic> authorComics = library.filterByCreator("Edition Tester");
    assertThat(authorComics).hasSize(2);

    // Verify edition types are preserved
    boolean hasSingleIssue =
        authorComics.stream()
            .anyMatch(comic -> comic.getRarityDetails().getEdition() == Edition.SINGLE_ISSUE);
    boolean hasTradePaperback =
        authorComics.stream()
            .anyMatch(comic -> comic.getRarityDetails().getEdition() == Edition.TRADE_PAPERBACK);

    assertTrue(hasSingleIssue, "Should have single issue comic");
    assertTrue(hasTradePaperback, "Should have trade paperback comic");
  }
}
