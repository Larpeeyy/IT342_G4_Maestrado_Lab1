// FILE: backend/src/main/java/com/example/miniapplication/Service/AuthService.java
package com.example.miniapplication.Service;

import com.example.miniapplication.Entity.User;
import com.example.miniapplication.Repository.UserRepository;
import com.example.miniapplication.dto.AuthResponse;
import com.example.miniapplication.dto.LoginRequest;
import com.example.miniapplication.dto.RegisterRequest;
import com.example.miniapplication.dto.UserResponse;
import com.example.miniapplication.security.JwtService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final UserRepository userRepo;
    private final PasswordEncoder encoder;
    private final JwtService jwt;

    public AuthService(UserRepository userRepo, PasswordEncoder encoder, JwtService jwt) {
        this.userRepo = userRepo;
        this.encoder = encoder;
        this.jwt = jwt;
    }

    public AuthResponse register(RegisterRequest req) {
        String email = req.getEmail().trim().toLowerCase();

        if (userRepo.existsByEmail(email)) {
            throw new RuntimeException("Email already used");
        }

        User user = new User();
        user.setEmail(email);
        user.setUsername(req.getUsername().trim());
        user.setPasswordHash(encoder.encode(req.getPassword())); // ✅ FIX: password_hash
        user.setFirstName(req.getFirstName().trim());
        user.setLastName(req.getLastName().trim());

        userRepo.save(user);

        String token = jwt.generateToken(user.getEmail());

        UserResponse userResponse = new UserResponse(
                user.getId(),
                user.getUsername(),
                user.getEmail(),
                user.getFirstName(),
                user.getLastName()
        );

        return new AuthResponse(token, userResponse);
    }

    public AuthResponse login(LoginRequest req) {
        String email = req.getEmail().trim().toLowerCase();

        User user = userRepo.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Invalid email or password"));

        // ✅ FIX: compare against password_hash
        if (!encoder.matches(req.getPassword(), user.getPasswordHash())) {
            throw new RuntimeException("Invalid email or password");
        }

        String token = jwt.generateToken(user.getEmail());

        UserResponse userResponse = new UserResponse(
                user.getId(),
                user.getUsername(),
                user.getEmail(),
                user.getFirstName(),
                user.getLastName()
        );

        return new AuthResponse(token, userResponse);
    }
}
