package com.api.demo.repos;

import org.springframework.data.jpa.repository.JpaRepository;

import com.api.demo.models.User;

public interface UserRepository extends JpaRepository<User, Long> {

    User findById(int id);

    User findByEmail(String email);

}
