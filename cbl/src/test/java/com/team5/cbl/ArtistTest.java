package com.team5.cbl;

import static org.junit.jupiter.api.Assertions.*;

import com.team5.cbl.cbl_app.objects.Artist;
import com.team5.cbl.cbl_app.objects.Creator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class ArtistTest {

  private Artist artist;

  @BeforeEach
  void setUp() {
    artist = new Artist("Picasso", 91, "Cubism");
  }

  @Test
  @DisplayName("Artist constructor sets all fields correctly")
  void artistConstructor() {
    assertEquals("Picasso", artist.getName());
    assertEquals(91, artist.getAge());
    assertEquals("Cubism", artist.getArtStyle());
  }

  @Test
  @DisplayName("Artist getArtStyle returns correct value")
  void artistGetArtStyle() {
    assertEquals("Cubism", artist.getArtStyle());
  }

  @Test
  @DisplayName("Artist setArtStyle updates the value correctly")
  void artistSetArtStyle() {
    artist.setArtStyle("Surrealism");
    assertEquals("Surrealism", artist.getArtStyle());
  }

  @Test
  @DisplayName("Artist setArtStyle to null works correctly")
  void artistSetArtStyleToNull() {
    artist.setArtStyle(null);
    assertNull(artist.getArtStyle());
  }

  @Test
  @DisplayName("Artist setArtStyle to empty string works correctly")
  void artistSetArtStyleToEmptyString() {
    artist.setArtStyle("");
    assertEquals("", artist.getArtStyle());
  }

  @Test
  @DisplayName("Artist toString returns expected format")
  void artistToString() {
    String expected = "Artist{name='Picasso', age=91, artStyle='Cubism'}";
    assertEquals(expected, artist.toString());
  }

  @Test
  @DisplayName("Artist toString with null art style")
  void artistToStringWithNullArtStyle() {
    Artist artistWithNullStyle = new Artist("Van Gogh", 37, null);
    String expected = "Artist{name='Van Gogh', age=37, artStyle='null'}";
    assertEquals(expected, artistWithNullStyle.toString());
  }

  @Test
  @DisplayName("Artist equals returns true for identical artists")
  void artistEquals() {
    Artist artist1 = new Artist("Picasso", 91, "Cubism");
    Artist artist2 = new Artist("Picasso", 91, "Cubism");

    assertEquals(artist1, artist2);
    assertEquals(artist2, artist1); // symmetry
  }

  @Test
  @DisplayName("Artist equals returns false for different art styles")
  void artistEqualsWithDifferentArtStyles() {
    Artist artist1 = new Artist("Picasso", 91, "Cubism");
    Artist artist2 = new Artist("Picasso", 91, "Surrealism");

    assertNotEquals(artist1, artist2);
  }

  @Test
  @DisplayName("Artist equals returns false for different names")
  void artistEqualsWithDifferentNames() {
    Artist artist1 = new Artist("Picasso", 91, "Cubism");
    Artist artist2 = new Artist("Van Gogh", 91, "Cubism");

    assertNotEquals(artist1, artist2);
  }

  @Test
  @DisplayName("Artist equals returns false for different ages")
  void artistEqualsWithDifferentAges() {
    Artist artist1 = new Artist("Picasso", 91, "Cubism");
    Artist artist2 = new Artist("Picasso", 37, "Cubism");

    assertNotEquals(artist1, artist2);
  }

  @Test
  @DisplayName("Artist equals returns true when comparing to self")
  void artistEqualsWithSelf() {
    assertEquals(artist, artist);
  }

  @Test
  @DisplayName("Artist equals returns false when comparing to null")
  void artistEqualsWithNull() {
    assertNotEquals(null, artist);
  }

  @Test
  @DisplayName("Artist equals returns false when comparing to different class")
  void artistEqualsWithDifferentClass() {
    Creator creator = new Creator("Picasso", 91);
    assertNotEquals(artist, creator);
  }

  @Test
  @DisplayName("Artist equals handles null art style correctly")
  void artistEqualsWithNullArtStyle() {
    Artist artist1 = new Artist("Van Gogh", 37, null);
    Artist artist2 = new Artist("Van Gogh", 37, null);
    Artist artist3 = new Artist("Van Gogh", 37, "Post-Impressionism");

    assertEquals(artist1, artist2);
    assertNotEquals(artist1, artist3);
    assertNotEquals(artist3, artist1);
  }

  @Test
  @DisplayName("Artist hashCode is consistent for equal objects")
  void artistHashCode() {
    Artist artist1 = new Artist("Picasso", 91, "Cubism");
    Artist artist2 = new Artist("Picasso", 91, "Cubism");

    assertEquals(artist1.hashCode(), artist2.hashCode());
  }

  @Test
  @DisplayName("Artist hashCode is different for different objects")
  void artistHashCodeForDifferentObjects() {
    Artist artist1 = new Artist("Picasso", 91, "Cubism");
    Artist artist2 = new Artist("Van Gogh", 37, "Post-Impressionism");

    assertNotEquals(artist1.hashCode(), artist2.hashCode());
  }

  @Test
  @DisplayName("Artist hashCode handles null art style correctly")
  void artistHashCodeWithNullArtStyle() {
    Artist artist1 = new Artist("Van Gogh", 37, null);
    Artist artist2 = new Artist("Van Gogh", 37, null);

    assertEquals(artist1.hashCode(), artist2.hashCode());
  }

  @Test
  @DisplayName("Artist extends Creator properly")
  void artistInheritance() {
    assertTrue(artist instanceof Creator);
    assertTrue(artist instanceof Artist);
  }

  @Test
  @DisplayName("Multiple artists can be created with different values")
  void multipleArtistsCreation() {
    Artist leonardo = new Artist("Leonardo da Vinci", 67, "Renaissance");
    Artist monet = new Artist("Claude Monet", 86, "Impressionism");
    Artist jackson = new Artist("Jackson Pollock", 44, "Abstract Expressionism");

    assertEquals("Leonardo da Vinci", leonardo.getName());
    assertEquals("Renaissance", leonardo.getArtStyle());

    assertEquals("Claude Monet", monet.getName());
    assertEquals("Impressionism", monet.getArtStyle());

    assertEquals("Jackson Pollock", jackson.getName());
    assertEquals("Abstract Expressionism", jackson.getArtStyle());

    // Ensure they're all different
    assertNotEquals(leonardo, monet);
    assertNotEquals(monet, jackson);
    assertNotEquals(leonardo, jackson);
  }
}
