package com.api.demo.repos;

import com.api.demo.models.EventModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventModelRepo extends JpaRepository<EventModel, Long> {

    EventModel findById(int id);

    Iterable<EventModel> findAllByIsPublicTrue();

    Iterable<EventModel> findAllByOrganizerId(int organizerId);
}
