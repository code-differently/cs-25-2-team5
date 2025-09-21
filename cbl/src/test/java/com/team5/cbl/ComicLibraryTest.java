/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.team5.cbl;

import java.time.Year;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

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

/**
 * @author vscode
 */
public class ComicLibraryTest {

  private ComicLibrary classUnderTest;
  private List<Comic> comics;
  private List<Genre> genres;
  private Creator writer;
  private Creator artist;
  private Character leadingCharacter;
  private Publisher publisher;
  private Comic comic1;
  private Comic comic2;


  @BeforeEach
  public void setUp() {
    // Set up test data
    genres = List.of(Genre.SUPERHERO, Genre.ACTION);
    writer = new Creator("Test Creator", 95);
    artist = new Creator("Jack Kirby", 76);
    leadingCharacter = new Character("Test name", "Peter Parker");
    publisher = new Publisher(CompanyName.DC_COMICS);

    // Create comics with creative teams
    List<Creator> creativeTeam1 = List.of(writer, artist);

    comic1 =
        new Comic(
            "Test comic 1",
            genres,
            100,
            new RarityDetails(Edition.FIRST_EDITION, 100, 8.0, Year.now()),
            creativeTeam1,
            leadingCharacter,
            publisher);

    List<Creator> creativeTeam2 = List.of(writer);
    comic2 =
        new Comic(
            "Test comic 2",
            genres,
            120,
            new RarityDetails(Edition.SECOND_EDITION, 120, 8.5, Year.now()),
            creativeTeam2,
            leadingCharacter,
            publisher);

    comics = List.of(comic1, comic2);
    classUnderTest = new ComicLibrary(comics);
  }

  @Test
  public void testFilterByComicTitle() {
    List<Comic> actual = classUnderTest.filterByComicTitle("Test comic");
    assertEquals(comics, actual);
  }

  @Test
  public void testFilterByComicTitle_NoComicsByTitle() {
    assertThatExceptionOfType(ComicNotFoundException.class)
        .isThrownBy(
            () -> {
              classUnderTest.filterByComicTitle("invalid title");
            })
        .withMessage("Comic title not found");
  }

  @Test
  public void testAddComic() {
    List<Creator> creativeTeam3 = List.of(writer);
    var comic3 =
        new Comic(
            "Test comic 3",
            genres,
            150,
            new RarityDetails(Edition.SINGLE_ISSUE, 150, 7.3, Year.now()),
            creativeTeam3,
            leadingCharacter,
            publisher);
    classUnderTest.addComic(comic3);
    assertEquals(3, classUnderTest.getComics().size());
  }

  @Test
  public void testremoveComic() {
    classUnderTest.removeComic(comic2);
    assertEquals(1, classUnderTest.getComics().size());
  }

  @Test
  public void testAddComic_AlreadyExists() {
    assertThatExceptionOfType(ComicNotFoundException.class)
        .isThrownBy(
            () -> {
              classUnderTest.addComic(comic1);
            })
        .withMessage("Comic already exists");
  }

  @Test
  public void testRemoveComic_NotFound() {
    List<Creator> creativeTeam3 = List.of(writer);
    var comic3 =
        new Comic(
            "Test comic 3",
            genres,
            150,
            new RarityDetails(Edition.SINGLE_ISSUE, 150, 7.3, Year.now()),
            creativeTeam3,
            leadingCharacter,
            publisher);
    assertThatExceptionOfType(ComicNotFoundException.class)
        .isThrownBy(
            () -> {
              classUnderTest.removeComic(comic3);
            })
        .withMessage("Comic title not found");
  }

  @Test
  public void testFilterByCharacter() {
    List<Comic> actual = classUnderTest.filterByCharacter("Test name");
    assertEquals(comics, actual);
  }

  @Test
  public void testFilterByCharacter_NoComicsByCharacter() {
    assertThatExceptionOfType(ComicNotFoundException.class)
        .isThrownBy(
            () -> {
              classUnderTest.filterByCharacter("invalid hero");
            })
        .withMessage("Character not found");
  }

  @Test
  public void testFilterByGenre() {
    List<Comic> actual = classUnderTest.filterByGenre(Genre.SUPERHERO);
    assertEquals(comics, actual);
  }

  @Test
  public void testFilterByGenre_NoComicsByGenre() {
    assertThatExceptionOfType(ComicNotFoundException.class)
        .isThrownBy(
            () -> {
              classUnderTest.filterByGenre(Genre.HORROR);
            })
        .withMessage("Genre not found");
  }

  @Test
  public void testFilterByCreator() {
    List<Comic> actual = classUnderTest.filterByCreator("Test Creator");
    assertEquals(comics, actual);
  }

  @Test
  public void testFilterByCreator_NoComicsByCreator() {
    assertThatExceptionOfType(ComicNotFoundException.class)
        .isThrownBy(
            () -> {
              classUnderTest.filterByCreator("invalid creator");
            })
        .withMessage("Creator not found");
  }

  @Test
  public void testFilterByPublisher() {
    List<Comic> actual = classUnderTest.filterByPublisher(CompanyName.DC_COMICS);
    assertEquals(comics, actual);
  }

  @Test
  public void testFilterByPublisher_NoComicsByPublisher() {
    assertThatExceptionOfType(ComicNotFoundException.class)
        .isThrownBy(
            () -> {
              classUnderTest.filterByPublisher(CompanyName.MARVEL_COMICS);
            })
        .withMessage("Publisher not found");
  }

  @Test
  public void testGetComics() {
    List<Comic> actual = classUnderTest.getComics();
    assertEquals(comics, actual);
  }

  @Test
  public void getRankingsTest() {
    List<Creator> creativeTeam3 = List.of(writer);
    var comic3 =
        new Comic(
            "Test comic 3 ",
            genres,
            150,
            new RarityDetails(Edition.TRADE_PAPERBACK, 150, 9.1, Year.now()),
            creativeTeam3,
            leadingCharacter,
            publisher);

    classUnderTest.addComic(comic3);

    List<Comic> expected = new ArrayList<>();
    expected.add(comic3);
    expected.add(comic2);
    expected.add(comic1);
    assertEquals(expected.get(0), classUnderTest.getRankings().get(0));
    assertEquals(expected.get(1), classUnderTest.getRankings().get(1));
    assertEquals(expected.get(2), classUnderTest.getRankings().get(2));
  }

  @Test
  public void testCreativeTeamFunctionality() {
    // Test that comics now have creative teams
    List<Creator> team1 = comic1.getCreativeTeam();
    assertEquals(2, team1.size());
    assertEquals(writer, team1.get(0));
    assertEquals(artist, team1.get(1));

    List<Creator> team2 = comic2.getCreativeTeam();
    assertEquals(1, team2.size());
    assertEquals(writer, team2.get(0));
  }

  @Test
  public void testBackwardCompatibilityGetHeadWriter() {
    // Test that getHeadWriter() still works for backward compatibility
    assertEquals(writer, comic1.getHeadWriter());
    assertEquals(writer, comic2.getHeadWriter());
  }

  @Test
  public void testAddAndRemoveCreator() {
    Creator newCreator = new Creator("John Romita", 90);

    // Test adding creator
    comic1.addCreator(newCreator);
    assertEquals(3, comic1.getCreativeTeam().size());

    // Test removing creator
    comic1.removeCreator(newCreator);
    assertEquals(2, comic1.getCreativeTeam().size());
  }

}
