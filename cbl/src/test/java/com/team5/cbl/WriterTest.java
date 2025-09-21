package com.team5.cbl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.team5.cbl.cbl_app.objects.Writer;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.Test;

public class WriterTest {

  @Test
  public void testWriterConstructorAndInheritance() {
    Writer writer = new Writer("Stan Lee", 95);
    assertEquals("Stan Lee", writer.getName());
    assertEquals(95, writer.getAge());
    assertTrue(writer.getPublishedWorks().isEmpty());
  }

  @Test
  public void testWriterWithPublishedWorks() {
    List<String> works = Arrays.asList("Spider-Man", "X-Men");
    Writer writer = new Writer("Stan Lee", 95, works);
    assertEquals("Stan Lee", writer.getName());
    assertEquals(95, writer.getAge());
    assertEquals(2, writer.getPublishedWorks().size());
    assertTrue(writer.getPublishedWorks().contains("Spider-Man"));
  }

  @Test
  public void testAddPublishedWork() {
    Writer writer = new Writer("Alan Moore", 70);
    writer.addPublishedWork("Watchmen");
    assertEquals(1, writer.getPublishedWorks().size());
    assertTrue(writer.getPublishedWorks().contains("Watchmen"));
  }

  @Test
  public void testRemovePublishedWork() {
    Writer writer =
        new Writer("Frank Miller", 67, Arrays.asList("The Dark Knight Returns", "Sin City"));
    writer.removePublishedWork("Sin City");
    assertEquals(1, writer.getPublishedWorks().size());
    assertFalse(writer.getPublishedWorks().contains("Sin City"));
  }

  @Test
  public void testToString() {
    Writer writer = new Writer("Neil Gaiman", 63, Arrays.asList("Sandman"));
    String expected = "Writer{name='Neil Gaiman', age=63, publishedWorks=[Sandman]}";
    assertEquals(expected, writer.toString());
  }

  @Test
  public void testEqualsAndHashCode() {
    Writer writer1 = new Writer("Grant Morrison", 55, Arrays.asList("Batman"));
    Writer writer2 = new Writer("Grant Morrison", 55, Arrays.asList("Batman"));
    Writer writer3 = new Writer("Grant Morrison", 55, Arrays.asList("Superman"));

    assertEquals(writer1, writer2);
    assertNotEquals(writer1, writer3);
    assertEquals(writer1.hashCode(), writer2.hashCode());
  }
}
