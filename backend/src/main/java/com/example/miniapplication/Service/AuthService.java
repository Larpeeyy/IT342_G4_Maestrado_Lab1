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
        String email = req.email.trim().toLowerCase();
        String username = req.username.trim();

        if (userRepo.existsByEmail(email))
            throw new IllegalArgumentException("Email already used");
        if (userRepo.existsByUsername(username))
            throw new IllegalArgumentException("Username already used");

        User u = new User();
        u.setEmail(email);
        u.setUsername(username);
        u.setPasswordHash(encoder.encode(req.password));
        userRepo.save(u);

        String token = jwt.generateToken(u.getEmail());
        return new AuthResponse(token, new UserResponse(u.getId(), u.getUsername(), u.getEmail()));
    }

    public AuthResponse login(LoginRequest req) {
        String email = req.email.trim().toLowerCase();

        User u = userRepo.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("Invalid credentials"));

        if (!encoder.matches(req.password, u.getPasswordHash())) {
            throw new IllegalArgumentException("Invalid credentials");
        }

        String token = jwt.generateToken(u.getEmail());
        return new AuthResponse(token, new UserResponse(u.getId(), u.getUsername(), u.getEmail()));
    }
}
