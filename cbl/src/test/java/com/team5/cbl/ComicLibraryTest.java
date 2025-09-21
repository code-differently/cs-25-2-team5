/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.team5.cbl;

import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.junit.jupiter.api.Assertions.assertEquals;

import com.team5.cbl.cbl_app.enums.CompanyName;
import com.team5.cbl.cbl_app.enums.Edition;
import com.team5.cbl.cbl_app.enums.Genre;
import com.team5.cbl.cbl_app.exceptions.ComicNotFoundException;
import com.team5.cbl.cbl_app.objects.Comic;
import com.team5.cbl.cbl_app.objects.ComicLibrary;
import com.team5.cbl.cbl_app.objects.Creator;
import com.team5.cbl.cbl_app.objects.Publisher;
import com.team5.cbl.cbl_app.objects.RarityDetails;
import java.time.Year;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * @author vscode
 */
public class ComicLibraryTest {
  private ComicLibrary classUnderTest;
  private Comic comic1;
  private Comic comic2;
  List<Comic> comics;
  List<Genre> genres = new ArrayList<>();
  Creator writer;
  Publisher publisher;
  com.team5.cbl.cbl_app.objects.Character leadingCharacter;

  @BeforeEach
  public void setUp() {

    comics = new ArrayList<>();
    genres.add(Genre.SUPERHERO);
    genres.add(Genre.ACTION);
    writer = new Creator("Test Creator", 45);
    leadingCharacter = new com.team5.cbl.cbl_app.objects.Character("Test name", "This is a hero");
    publisher = new Publisher(CompanyName.DC_COMICS);

    comic1 =
        new Comic(
            "Test comic",
            genres,
            new RarityDetails(Edition.SINGLE_ISSUES, 150, 7.8, Year.now()),
            writer,
            leadingCharacter,
            publisher);

    comic2 =
        new Comic(
            "Test comic",
            genres,
            new RarityDetails(Edition.TRADE_PAPERBACKS, 150, 8.2, Year.now()),
            writer,
            leadingCharacter,
            publisher);

    comics.add(comic1);
    comics.add(comic2);
    classUnderTest = new ComicLibrary(comics);
  }

  @Test
  public void testFilterByComicTitle() {
    List<Comic> actual = classUnderTest.filterByComicTitle("Test comic");
    assertEquals(actual, comics);
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
    var comic3 =
        new Comic(
            "Test comic",
            genres,
            new RarityDetails(Edition.SINGLE_ISSUES, 150, 7.3, Year.now()),
            writer,
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
    var comic3 =
        new Comic(
            "Test comic",
            genres,
            new RarityDetails(Edition.SINGLE_ISSUES, 150, 7.3, Year.now()),
            writer,
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
    assertEquals(actual, comics);
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
    assertEquals(actual, comics);
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
    assertEquals(actual, comics);
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
    assertEquals(actual, comics);
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
    assertEquals(actual, comics);
  }

  @Test
  public void getRankingsTest() {

    var comic3 =
        new Comic(
            "Test comic 3 ",
            genres,
            new RarityDetails(Edition.TRADE_PAPERBACKS, 150, 9.1, Year.now()),
            writer,
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
}
