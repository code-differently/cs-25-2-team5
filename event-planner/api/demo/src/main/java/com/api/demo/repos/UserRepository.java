package com.api.demo.repos;

import com.api.demo.dtos.UserInviteDTO;
import com.api.demo.models.User;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UserRepository extends JpaRepository<User, Long> {

  Optional<User> findById(int id);

  Optional<User> findByEmail(String email);

  Set<User> findAllByEmailIn(Set<String> emails);

  Optional<User> findByClerkId(String clerkId);

  @Query("""
    SELECT new com.api.demo.dtos.UserInviteDTO(
        u.email, u.name, e.description, e.startTime, e.title,e.id,e.location,e.imageURL
    )
    FROM EventGuest eg
    JOIN eg.guest u
    JOIN eg.event e
    WHERE u.id = :userId
""")
  List<UserInviteDTO> findAllUserInvitedEvents(Long userId);
}
