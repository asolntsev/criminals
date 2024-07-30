package services;

import models.User;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.Optional;

@Singleton
public class LoginService {
  @Inject UserRepository userRepository;

  public Optional<User> login(String username, String password) {
    return userRepository.byUsername(username)
      .filter(u -> u.password().equals(password));
  }

  public User byUsername(String username) {
    return userRepository.byUsername(username)
      .orElseThrow(() -> new IllegalArgumentException("User not found: " + username));
  }
}
