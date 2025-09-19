/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.team5.cbl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

import com.team5.cbl.cbl_app.enums.CompanyName;
import com.team5.cbl.cbl_app.enums.Edition;
import com.team5.cbl.cbl_app.enums.Genre;
import com.team5.cbl.cbl_app.objects.Comic;
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
            new RarityDetails(Edition.SINGLE_ISSUES, 150, 7.3, Year.now()),
            writer,
            leadingCharacter,
            publisher);
  }

  @Test
  public void getterTest() {
    List<Genre> expected = new ArrayList<>();
    RarityDetails expectedRarityDetails =
        new RarityDetails(Edition.SINGLE_ISSUES, 150, 7.3, Year.now());
    var expectedPublisher = new Publisher(CompanyName.DC_COMICS);
    var expectedLeadingCharacter =
        new com.team5.cbl.cbl_app.objects.Character("Test name", "This is a hero");

    expected.add(Genre.ACTION);
    expected.add(Genre.ADVENTURE);
    assertEquals(comic.getTitle(), "Test comic");
    assertEquals(comic.getGenre(), expected);
    assertEquals(comic.getRarityDetails(), expectedRarityDetails);

    assertThat(expectedLeadingCharacter.equals(comic.getLeadingCharacter()));
    assertThat(expectedPublisher.equals(comic.getPublisher()));
  }
}
