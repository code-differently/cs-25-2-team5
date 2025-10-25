package com.api.demo.repos;

import com.api.demo.models.EventGuest;
import com.api.demo.models.EventGuestKey;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EventGuestRepo extends JpaRepository<EventGuest, EventGuestKey> {

  List<EventGuest> findAllByEventGuestKeyEventId(Long eventId);

  List<EventGuest> findAllByEventGuestKeyGuestId(Long guestId);

  Optional<EventGuest> findByEventGuestKey(EventGuestKey eventGuestKey);
}
