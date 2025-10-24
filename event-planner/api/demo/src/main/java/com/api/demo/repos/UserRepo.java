package com.api.demo.repos;

import com.api.demo.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepo extends JpaRepository<User, Long> {

    User findById(int id);

    User findByEmail(String email);
}
