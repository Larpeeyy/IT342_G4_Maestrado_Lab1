package com.example.miniapplication.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class UpdateUsernameRequest {

    @NotBlank
    @Size(min = 3, max = 50)
    public String username;
}
