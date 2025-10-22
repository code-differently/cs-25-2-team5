package com.api.demo.repos;

import com.api.demo.models.User;
import java.util.Optional;
import java.util.Set;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

  Optional<User> findById(int id);

  Optional<User> findByEmail(String email);

  Set<User> findAllByEmailIn(Set<String> emails);
}
