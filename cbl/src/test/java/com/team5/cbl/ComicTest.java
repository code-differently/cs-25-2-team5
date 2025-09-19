/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.team5.cbl;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.team5.cbl.cbl_app.enums.Edition;
import com.team5.cbl.cbl_app.enums.Genre;
import com.team5.cbl.cbl_app.objects.Comic;
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
    
    genres.add(Genre.ACTION);
    genres.add(Genre.ADVENTURE);
    comic =
        new Comic(
            "Test comic", genres, new RarityDetails(Edition.SINGLE_ISSUES, 150, 7.3, Year.now()),);
  }

  @Test
  public void getterTest() {
    List<Genre> expected = new ArrayList<>();
    RarityDetails expectedRarityDetails =
        new RarityDetails(Edition.SINGLE_ISSUES, 150, 7.3, Year.now());
    expected.add(Genre.ACTION);
    expected.add(Genre.ADVENTURE);
    assertEquals(comic.getTitle(), "Test comic");
    assertEquals(comic.getGenre(), expected);
    assertEquals(comic.getRarityDetails(), expectedRarityDetails);
  }
}
