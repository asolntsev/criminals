package services;

import models.User;

import javax.inject.Singleton;
import java.util.List;
import java.util.Optional;

@Singleton
public class UserRepository {
  private final List<User> users = List.of(
    new User("John", "Smith", "john@smith.ee", "38012310010", "john", "secret")
  );

  public Optional<User> byUsername(String username) {
    return users.stream()
      .filter(u -> u.username().equals(username))
      .findAny();
  }
}
