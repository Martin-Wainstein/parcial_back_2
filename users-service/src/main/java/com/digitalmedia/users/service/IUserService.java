package com.digitalmedia.users.service;

import com.digitalmedia.users.model.User;

import java.util.List;
import java.util.Optional;

public interface IUserService {
  User validateAndGetUserExtra(String username);

  Optional<User> getUserExtra(String username);

  User saveUserExtra(User userExtra);

  List<User> getNonAdminUsers();
}
