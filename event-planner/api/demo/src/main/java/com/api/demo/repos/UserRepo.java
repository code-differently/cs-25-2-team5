package com.api.demo.repos;

import org.springframework.data.jpa.repository.JpaRepository;

import com.api.demo.models.User;

public interface UserRepo extends JpaRepository<User, Long> {

}
