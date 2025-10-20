package com.api.demo.repos;

import com.api.demo.models.User;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepo extends JpaRepository<User, Long> {

  Optional<User> findById(Long id);

  Optional<User> findByEmail(String email);
}
