/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.team5.cbl;

import java.util.ArrayList;
import java.util.List;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.team5.cbl.cbl_app.Comic;
import com.team5.cbl.cbl_app.Genre;



/**
 *
 * @author vscode
 */
public class ComicTest {
    private Comic comic;

    @BeforeEach
    public void setUp() {
        List<Genre> genres = new ArrayList<>();
        genres.add(Genre.ACTION);
        genres.add(Genre.ADVENTURE);
        comic = new Comic("Test comic",genres);
    }

    @Test
    public void getterTest() {
        List<Genre> expected = new ArrayList<>();
        expected.add(Genre.ACTION);
        expected.add(Genre.ADVENTURE);
        assertEquals(comic.getTitle(),"Test comic");
        assertEquals(comic.getGenre(),expected);
    }

}
