package com.team5.cbl;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class CharacterTest {

  @Test
  public void testConstructorAndGetters() {
    Character character = new Character("Superman", "Man of Steel");
    assertEquals("Superman", character.getHeroName());
    assertEquals("Man of Steel", character.getBio());
  }

  @Test
  public void testToString() {
    Character character = new Character("Batman", "Dark Knight");
    String expected = "Character{heroName='Batman', bio='Dark Knight'}";
    assertEquals(expected, character.toString());
  }
}
