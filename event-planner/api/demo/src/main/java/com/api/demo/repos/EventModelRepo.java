package com.api.demo.repos;

import com.api.demo.models.EventModel;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventModelRepo extends JpaRepository<EventModel, Long> {

  Optional<EventModel> findById(Long id);

  Iterable<EventModel> findAllByIsPublicTrue();

  Iterable<EventModel> findAllByOrganizerId(Long organizerId);
}
