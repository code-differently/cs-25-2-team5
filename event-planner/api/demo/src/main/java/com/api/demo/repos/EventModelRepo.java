package com.api.demo.repos;

import com.api.demo.models.EventModel;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventModelRepo extends JpaRepository<EventModel, Long> {

<<<<<<< HEAD
    EventModel findById(int id);
=======
  Optional<EventModel> findById(Long id);
>>>>>>> main

    Iterable<EventModel> findAllByIsPublicTrue();

<<<<<<< HEAD
    Iterable<EventModel> findAllByOrganizerId(int organizerId);
=======
  Iterable<EventModel> findAllByOrganizerId(Long organizerId);
>>>>>>> main
}
