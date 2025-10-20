package com.api.demo.repos;

import com.api.demo.models.EventGuest;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventGuestRepo extends JpaRepository<EventGuest, Long> {
    
  Iterable<EventGuest> findAllByEventId(int eventId);
  Iterable<EventGuest> findAllByUserId(int userId);
}
