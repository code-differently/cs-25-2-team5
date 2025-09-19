package com.team5.cbl;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.team5.cbl.cbl_app.objects.Creator;

class CreatorTest {

  @Test
  @DisplayName("Constructor sets fields; getters return values")
  void constructorAndGetters() {
    Creator creator = new Creator("Joy Brown", 39);
    assertEquals("Joy Brown", creator.getName());
    assertEquals(39, creator.getAge());
  }

  @Test
  @DisplayName("Different creators have independent values")
  void multipleCreators() {
    Creator key = new Creator("Key", 18);
    Creator zach = new Creator("Zach", 17);
    Creator pyes = new Creator("Pyes", 15);

    assertEquals("Key", key.getName());
    assertEquals(18, key.getAge());

    assertEquals("Zach", zach.getName());
    assertEquals(17, zach.getAge());

    assertEquals("Pyes", pyes.getName());
    assertEquals(15, pyes.getAge());
  }
}
