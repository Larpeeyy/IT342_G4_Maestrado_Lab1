package com.example.miniapplication.dto;

public class UserResponse {

  private Long id;
  private String username;
  private String email;
  private String firstName;
  private String lastName;

  public UserResponse(Long id, String username, String email, String firstName, String lastName) {
    this.id = id;
    this.username = username;
    this.email = email;
    this.firstName = firstName;
    this.lastName = lastName;
  }

  public Long getId() { return id; }
  public String getUsername() { return username; }
  public String getEmail() { return email; }
  public String getFirstName() { return firstName; }
  public String getLastName() { return lastName; }
}
