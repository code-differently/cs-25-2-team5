package com.api.demo.repos;

import org.springframework.data.jpa.repository.JpaRepository;

import com.api.demo.models.EventModel;

public interface EventModelRepo extends JpaRepository<EventModel, Long> {

}
