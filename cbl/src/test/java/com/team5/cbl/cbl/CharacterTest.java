package com.team5.cbl.cbl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

public class CharacterTest {

    @Test
    public void testConstructorAndGetters() {
        Character character = new Character("Superman", "DC Comics", "Man of Steel");
        assertEquals("Superman", character.getHeroName());
        assertEquals("DC Comics", character.getPublisher());
        assertEquals("Man of Steel", character.getBio());
    }

    @Test
    public void testToString() {
        Character character = new Character("Batman", "DC Comics", "Dark Knight");
        String expected = "Character{heroName='Batman', publisher='DC Comics', bio='Dark Knight'}";
        assertEquals(expected, character.toString());
    }
}