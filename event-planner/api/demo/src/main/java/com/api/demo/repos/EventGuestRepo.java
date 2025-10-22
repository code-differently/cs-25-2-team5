package com.api.demo.repos;

import com.api.demo.models.EventGuest;
import com.api.demo.models.EventGuestKey;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface EventGuestRepo extends JpaRepository<EventGuest, EventGuestKey> {

  Iterable<EventGuest> findAllByEventGuestKeyEventId(Long eventId);

  Iterable<EventGuest> findAllByEventGuestKeyGuestId(Long guestId);
  Optional<EventGuest> findByEventGuestKey(EventGuestKey eventGuestKey);
  @Query("SELECT * FROM event_guests INNER JOIN on  ")
  Boolean findAllEventsByUserId(@Param("userId") Long userId);
}
