/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.team5.cbl;

import java.time.Year;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.team5.cbl.cbl_app.enums.CompanyName;
import com.team5.cbl.cbl_app.enums.Edition;
import com.team5.cbl.cbl_app.enums.Genre;
import com.team5.cbl.cbl_app.exceptions.ComicNotFoundException;
import com.team5.cbl.cbl_app.objects.Comic;
import com.team5.cbl.cbl_app.objects.ComicLibrary;
import com.team5.cbl.cbl_app.objects.Creator;
import com.team5.cbl.cbl_app.objects.Publisher;
import com.team5.cbl.cbl_app.objects.RarityDetails;

/**
 *
 * @author vscode
 */
public class ComicLibraryTest {
    private ComicLibrary classUnderTest;
    private Comic comic1;
    private Comic comic2;
    List<Comic> comics;

    @BeforeEach
    public void setUp(){

    comics = new ArrayList<>();
    List<Genre> genres = new ArrayList<>();
    genres.add(Genre.SUPERHERO);
    genres.add(Genre.ACTION);

    Creator writer = new Creator("Test Creator", 45);
    var leadingCharacter =
        new com.team5.cbl.cbl_app.objects.Character("Test name", "This is a hero");
    var publisher = new Publisher(CompanyName.DC_COMICS);
     comic1 =
        new Comic(
            "Test comic",
            genres,
            new RarityDetails(Edition.SINGLE_ISSUES, 150, 7.3, Year.now()),
            writer,
            leadingCharacter,
            publisher);

     comic2 =
        new Comic(
            "Test comic",
            genres,
            new RarityDetails(Edition.TRADE_PAPERBACKS, 150, 7.3, Year.now()),
            writer,
            leadingCharacter,
            publisher);

            classUnderTest = new ComicLibrary(comics);

            comics.add(comic1);
            comics.add(comic2);
        }

    @Test
    public void testFilterByComicTitle() {
        List<Comic> actual = classUnderTest.filterByComicTitle("Test comic");
        assertEquals(actual, comics);
    }


    @Test 
    public void testFilterByComicTitle_NoComicsByTitle() {
        
        assertThatExceptionOfType(ComicNotFoundException.class)
        .isThrownBy(()-> {
            classUnderTest.filterByComicTitle("invalid title");
        })
        .withMessage("Comic title not found");
    }


}
