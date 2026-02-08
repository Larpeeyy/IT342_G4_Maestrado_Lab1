package com.example.miniapplication.controller;

import com.example.miniapplication.dto.TaskDTO;
import com.example.miniapplication.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "http://localhost:3000") // Connects to your React app
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody UserDTO userDto) {
        userService.saveUser(userDto);
        return ResponseEntity.ok("User registered successfully");
    }
}