package com.api.demo.services;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.api.demo.dtos.UserInviteDTO;
import com.api.demo.exceptions.UserNotFoundException;
import com.api.demo.models.EventModel;
import com.api.demo.models.User;
import com.api.demo.repos.UserRepository;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
@DisplayName("User Service Test")
public class UserServiceTest {

  @Mock private UserRepository userRepository;

  @Mock private EventService eventService;

  @InjectMocks private UserService userService;

  private User testUser;

  @BeforeEach
  void setUp() {
    testUser = new User();
    testUser.setId(1L);
    testUser.setName("John Doe");
    testUser.setEmail("john@example.com");
    testUser.setPassword("password123");
    // Initialize the organizedEvents collection to avoid NullPointerException
    testUser.setOrganizedEvents(new java.util.HashSet<>());
  }

  @Test
  @DisplayName("Should return user when valid ID is provided")
  void shouldReturnUser_WhenValidIdProvided() {
    // Given
    Long userId = 1L;
    when(userRepository.findById(userId)).thenReturn(Optional.of(testUser));

    // When
    User result = userService.getUserById(userId);

    // Then
    assertThat(result).isNotNull();
    assertThat(result.getId()).isEqualTo(userId);
    assertThat(result.getName()).isEqualTo("John Doe");
    assertThat(result.getEmail()).isEqualTo("john@example.com");
  }

  @Test
  @DisplayName("Should throw UserNotFoundException when user ID does not exist")
  void shouldThrowUserNotFoundException_WhenUserIdDoesNotExist() {
    // Given
    Long nonExistentId = 999L;
    when(userRepository.findById(nonExistentId)).thenReturn(Optional.empty());

    // When & Then
    assertThatThrownBy(() -> userService.getUserById(nonExistentId))
        .isInstanceOf(UserNotFoundException.class)
        .hasMessage("User not found");
  }

  @Test
  @DisplayName("Should handle null ID appropriately")
  void shouldHandleNullId_Appropriately() {
    // Given
    Long nullId = null;

    // When & Then - This will likely throw a different exception (like IllegalArgumentException)
    // depending on how Spring Data JPA handles null IDs, but we test that it doesn't succeed
    assertThatThrownBy(() -> userService.getUserById(nullId))
        .isInstanceOf(
            Exception.class); // Could be UserNotFoundException or IllegalArgumentException
  }

  @Test
  @DisplayName("Should return user with correct properties when user exists")
  void shouldReturnUserWithCorrectProperties_WhenUserExists() {
    // Given
    User expectedUser = new User();
    expectedUser.setId(42L);
    expectedUser.setName("Jane Smith");
    expectedUser.setEmail("jane.smith@example.com");
    expectedUser.setPassword("securePassword");

    when(userRepository.findById(42L)).thenReturn(Optional.of(expectedUser));

    // When
    User actualUser = userService.getUserById(42L);

    // Then
    assertThat(actualUser).usingRecursiveComparison().isEqualTo(expectedUser);
  }

  @Test
  @DisplayName("Should create event and associate with user")
  void createEventTest() {
      // Given
      EventModel event = new EventModel();
      event.setTitle("Test Event");
      event.setDescription("This is a test event.");
      event.setIsPublic(true);
      event.setStartTime(LocalDateTime.now().plusDays(1));

      // Mock the repository and service calls
      when(userRepository.findById(1L)).thenReturn(Optional.of(testUser));

      // The service will set the organizer, so we need to return the modified event
      when(eventService.createEvent(event)).thenAnswer(invocation -> {
          EventModel eventArg = invocation.getArgument(0);
          return eventArg; // Return the same event that was passed in (now with organizer set)
      });

      // When
      EventModel createdEvent = userService.createPublicEvent(event, 1L);

      // Then
      assertThat(createdEvent).isNotNull();
      assertThat(createdEvent.getTitle()).isEqualTo("Test Event");
      assertThat(createdEvent.getOrganizer()).isEqualTo(testUser);

      // Verify that the user's organized events collection was updated
      assertThat(testUser.getOrganizedEvents()).contains(createdEvent);
  }


  @Test
  @DisplayName("Find all users by email emails found")
  public void getAllUsersFromEmailsTest_shouldReturnSetOfUsers() {
    // Given
    User user1 = new User();
    user1.setEmail("jane.smith@example.com");
    User user2 = new User();
    user2.setEmail("john.doe@example.com");
    User user3 = new User();
    user3.setEmail("tyrone.johnson@example.com");
    var emails = new HashSet<String>();
    emails.add("jane.smith@example.com");
    emails.add("john.doe@example.com");
    emails.add("tyrone.johnson@example.com");
    // When
    when(userRepository.findAllByEmailIn(emails))
    .thenReturn(Set.of(user1,user2,user3));
    Set<User> users = userService.getAllUsersFromEmails(emails);
    Set<String> userEmails = users
    .stream()
    .map(User::getEmail)
    .collect(Collectors.toSet());
    // Then
    assertEquals(users.size(),emails.size());
    assertEquals(emails, userEmails);
  }

  @Test
  @DisplayName("Find all users by email,email input with different cases")
  public void getAllUsersFromEmailsTest_shouldReturnSetOfUsersIgnoringCase() {
    User user1 = new User();
    user1.setEmail("jane.smith@example.com");
    User user2 = new User();
    user2.setEmail("john.doe@example.com");
    User user3 = new User();
    user3.setEmail("tyrone.johnson@example.com");
    var expectedEmails = new HashSet<String>();
    expectedEmails.add("Jane.Smith@example.com");
    expectedEmails.add("John.Doe@example.com");
    expectedEmails.add("Tyrone.Johnson@example.com");

    when(userRepository.findAllByEmailIn(expectedEmails))
    .thenReturn(Set.of(user1,user2,user3));

    Set<User> users = userService.getAllUsersFromEmails(expectedEmails);
    Set<String> userEmails = users
    .stream()
    .map(User::getEmail)
    .collect(Collectors.toSet());

    assertEquals(users.size(),expectedEmails.size());
    assertEquals(expectedEmails.stream().map(String::toLowerCase).collect(Collectors.toSet()), userEmails);

  }

  @Test
  @DisplayName("Find all users by email, no emails found ")
  public void getAllUsersFromEmailsTest_shouldReturnEmptySet() {
    var expectedEmails = new HashSet<String>();
    expectedEmails.add("Jane.Smith@example.com");
    expectedEmails.add("John.Doe@example.com");
    expectedEmails.add("Tyrone.Johnson@example.com");
    when(userRepository.findAllByEmailIn(expectedEmails)).thenReturn(Set.of());
    Set<User> users = userService.getAllUsersFromEmails(expectedEmails);
    assertEquals(users.size(), 0);
    

  }

  @Test
  @DisplayName("Test get all events a user has been invitied to")
  public void getUserInvitedEvents() {
    Long userId = 1L;
        List<UserInviteDTO> expectedInvites = List.of(
          UserInviteDTO.builder().email("john.doe@example.com").description("Description 1").title("Event 1").is_public(true).start_time(LocalDateTime.now().plusDays(1)).name("John Doe").build(),
          UserInviteDTO.builder().email("john.doe@example.com").description("Description 2").title("Event 2").is_public(false).start_time(LocalDateTime.now().plusDays(2)).name("John Doe").build()
        );

      when(userRepository.findAllUserInvitedEvents(userId)).thenReturn(expectedInvites);

        // When
        List<UserInviteDTO> result = userService.getAllInvitedEvents(userId);

        // Then
        verify(userRepository, times(1)).findAllUserInvitedEvents(userId);
        assertThat(result).hasSize(2);
        assertThat(result).isEqualTo(expectedInvites);
        assertThat(result.get(0).getEmail()).isEqualTo("john.doe@example.com");
        assertThat(result.get(1).getTitle()).isEqualTo("Event 2");

  }


}
