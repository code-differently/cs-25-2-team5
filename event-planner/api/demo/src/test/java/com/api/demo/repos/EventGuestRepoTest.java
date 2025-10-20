package com.api.demo.repos;

import static org.assertj.core.api.Assertions.assertThat;

import com.api.demo.models.EventGuest;
import com.api.demo.models.EventGuestKey;
import com.api.demo.models.EventModel;
import com.api.demo.models.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.TestPropertySource;

@DataJpaTest
@TestPropertySource(
    properties = {
      "spring.datasource.url=jdbc:h2:mem:testdb",
      "spring.datasource.driver-class-name=org.h2.Driver",
      "spring.jpa.hibernate.ddl-auto=create-drop"
    })
class EventGuestRepoTest {

  @Autowired private EventGuestRepo eventGuestRepo;

  @Test
  @DisplayName("Should save and retrieve EventGuest")
  void shouldSaveAndRetrieveEventGuest() {
    // Create composite key
    EventGuestKey key = new EventGuestKey(1L, 1L);

    // Create event and guest (minimal, not persisted for this test)
    EventModel event = new EventModel();
    event.setId(1L);
    event.setTitle("Test Event");

    User guest = new User();
    guest.setId(1);
    guest.setName("Test User");
    guest.setEmail("test@example.com");
    guest.setPassword("password");

    // Create and set up EventGuest
    EventGuest eventGuest = new EventGuest();
    eventGuest.setEventGuestKey(key);
    eventGuest.setEvent(event);
    eventGuest.setGuest(guest);

    EventGuest saved = eventGuestRepo.save(eventGuest);
    assertThat(saved).isNotNull();
    assertThat(eventGuestRepo.findById(saved.getEventGuestKey())).isPresent();
  }

  // Add more tests for custom queries if needed
}
