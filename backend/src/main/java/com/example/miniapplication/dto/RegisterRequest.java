package com.example.miniapplication.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class RegisterRequest {

  @NotBlank
  @Size(min = 3, max = 50)
  public String username;

  @NotBlank
  @Email
  public String email;

  @NotBlank
  @Size(min = 6, max = 72)
  public String password;
}
