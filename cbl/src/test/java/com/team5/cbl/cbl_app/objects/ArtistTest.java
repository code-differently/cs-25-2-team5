package com.team5.cbl.cbl_app.objects;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

public class ArtistTest {
    @Test
    void testGetArtStyle() {
        Artist artist = new Artist("Picasso", 91, "Cubism");
        assertEquals("Cubism", artist.getArtStyle());
    }

    @Test
    void testHashCode() {
        Artist artist1 = new Artist("Picasso", 91, "Cubism");
        Artist artist2 = new Artist("Picasso", 91, "Cubism");
        assertEquals(artist1.hashCode(), artist2.hashCode());
    }

    @Test
    void testSetArtStyle() {
        Artist artist = new Artist("Picasso", 91, "Cubism");
        artist.setArtStyle("Surrealism");
        assertEquals("Surrealism", artist.getArtStyle());
    }

    @Test
    void testToString() {
        Artist artist = new Artist("Picasso", 91, "Cubism");
        String expected = "Artist{name='Picasso', age=91, artStyle='Cubism'}";
        assertEquals(expected, artist.toString());
    }
}

