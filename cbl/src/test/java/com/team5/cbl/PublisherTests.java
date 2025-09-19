package com.team5.cbl;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class PublisherTests {

  private String testCompanyName;
  private List<String> testCharacters;

  @BeforeEach
  void setUp() {
    testCompanyName = "Marvel Comics";
    testCharacters = new ArrayList<>(Arrays.asList("Spider-Man", "Iron Man", "Captain America"));
  }

  @Test
  @DisplayName("Test constructor with company name and character list")
  void testConstructorWithCharacters() {
    Publisher publisher = new Publisher(testCompanyName, testCharacters);

    assertEquals(testCompanyName, publisher.getCompanyName());
    assertNotNull(publisher.getCharacters());
    assertFalse(publisher.getCharacters().isEmpty());
  }

  @Test
  @DisplayName("Test getCompanyName method")
  void testGetCompanyName() {
    Publisher publisher = new Publisher(testCompanyName, testCharacters);
    assertEquals(testCompanyName, publisher.getCompanyName());
  }

  @Test
  @DisplayName("Test setCompanyName method")
  void testSetCompanyName() {
    Publisher publisher = new Publisher(testCompanyName, testCharacters);
    String newCompanyName = "DC Comics";

    publisher.setCompanyName(newCompanyName);
    assertEquals(newCompanyName, publisher.getCompanyName());
  }

  @Test
  @DisplayName("Test setCharacters method")
  void testSetCharacters() {
    Publisher publisher = new Publisher(testCompanyName);
    List<String> newCharacters =
        new ArrayList<>(Arrays.asList("Batman", "Superman", "Wonder Woman"));

    publisher.setCharacters(newCharacters);
    assertEquals(newCharacters, publisher.getCharacters());

    newCharacters.add("Flash");
    assertFalse(publisher.getCharacters().contains("Flash"));
  }

  @Test
  @DisplayName("Test addCharacter - new character")
  void testAddCharacterNew() {
    Publisher publisher = new Publisher(testCompanyName, testCharacters);
    String newCharacter = "Black Widow";

    publisher.addCharacter(newCharacter);
    assertTrue(publisher.hasCharacter(newCharacter));
    assertEquals(4, publisher.getCharacters().size());
  }

  @Test
  @DisplayName("Test addCharacter - duplicate character")
  void testAddCharacterDuplicate() {
    Publisher publisher = new Publisher(testCompanyName, testCharacters);
    String existingCharacter = "Spider-Man";

    publisher.addCharacter(existingCharacter);
    assertTrue(publisher.hasCharacter(existingCharacter));
    assertEquals(3, publisher.getCharacters().size());
  }

  @Test
  @DisplayName("Test hasCharacter - existing character")
  void testHasCharacterExisting() {
    Publisher publisher = new Publisher(testCompanyName, testCharacters);
    assertTrue(publisher.hasCharacter("Spider-Man"));
    assertTrue(publisher.hasCharacter("Iron Man"));
    assertTrue(publisher.hasCharacter("Captain America"));
  }

  @Test
  @DisplayName("Test hasCharacter - non-existing character")
  void testHasCharacterNonExisting() {
    Publisher publisher = new Publisher(testCompanyName, testCharacters);
    assertFalse(publisher.hasCharacter("Batman"));
    assertFalse(publisher.hasCharacter("Superman"));
  }

  @Test
  @DisplayName("Test with null company name")
  void testNullCompanyName() {
    assertDoesNotThrow(
        () -> {
          Publisher publisher = new Publisher(null, testCharacters);
          assertNotNull(publisher.getCharacters());
        });
  }

  @Test
  @DisplayName("Test with null characters list")
  void testNullCharactersList() {
    assertDoesNotThrow(
        () -> {
          Publisher publisher = new Publisher(testCompanyName, null);
          assertNotNull(publisher.getCharacters());
        });
  }

  @Test
  @DisplayName("Test with empty characters list")
  void testEmptyCharactersList() {
    List<String> emptyList = new ArrayList<>();
    Publisher publisher = new Publisher(testCompanyName, emptyList);

    assertTrue(publisher.getCharacters().isEmpty());
    assertFalse(publisher.hasCharacter("Any Character"));
  }
}
