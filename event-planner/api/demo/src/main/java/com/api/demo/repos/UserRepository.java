package com.api.demo.repos;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.api.demo.models.User;

public interface UserRepository extends JpaRepository<User, Long> {


    Optional<User> findById(int id);

    Optional<User> findByEmail(String email);

}
