package com.digitalmedia.users.repository;

import com.digitalmedia.users.exceptions.UserExtraNotFoundException;
import org.keycloak.representations.idm.UserRepresentation;
import com.digitalmedia.users.model.User;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.representations.idm.GroupRepresentation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class UserRepository {

  @Autowired
  private Keycloak keycloakClient;

  @Value("digitalmedia")
  private String realm;

  private IUserRepository userRepository;

  private User toUserDTO(UserRepresentation userRepresentation){
    return new User(userRepresentation.getUsername());
  }

  public User validateAndGetUser(String username) {
    return userRepository.validateAndGetUser(username);
  }

  public Optional<User> getUserExtra(String username) {
    return userRepository.getUserExtra(username);
  }

  public User saveUserExtra(User user) {
    return userRepository.saveUserExtra(user);
  }

  public List<User> getNonAdminUsers() {
    List<UserRepresentation> usersFromKeycloak = keycloakClient.realm(realm).users().list();
    List<UserRepresentation> enabledUsers = usersFromKeycloak.stream().filter(userRepresentation -> {
      List<GroupRepresentation> groups = keycloakClient.realm(realm).users().get(userRepresentation.getId()).groups();
      return groups.stream().noneMatch(s -> Objects.equals(s.getName(), "admin"));
    }).collect(Collectors.toList());
    return enabledUsers.stream().map(this::toUserDTO).collect(Collectors.toList());
  }
}
