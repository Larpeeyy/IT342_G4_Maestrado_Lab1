package com.example.miniapplication.Service;

import com.example.miniapplication.Entity.User;
import com.example.miniapplication.Repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepo;

    public UserService(UserRepository userRepo) {
        this.userRepo = userRepo;
    }

    public User requireByEmail(String email) {
        return userRepo.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
    }

    public User updateUsername(String email, String newUsername) {
        newUsername = newUsername.trim();

        if (newUsername.length() < 3 || newUsername.length() > 50) {
            throw new IllegalArgumentException("Username must be 3 to 50 characters");
        }

        User u = requireByEmail(email);

        if (userRepo.existsByUsername(newUsername) && !newUsername.equalsIgnoreCase(u.getUsername())) {
            throw new IllegalArgumentException("Username already used");
        }

        u.setUsername(newUsername);
        return userRepo.save(u);
    }
}
