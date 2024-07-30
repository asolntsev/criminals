package models;

public record User(
  String firstName,
  String lastName,
  String email,
  String idNumber,
  String username,
  String password
) {
  public String fullName() {
    return firstName + " " + lastName;
  }
}
