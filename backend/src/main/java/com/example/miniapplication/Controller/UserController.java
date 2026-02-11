package com.example.miniapplication.Controller;

import com.example.miniapplication.Entity.User;
import com.example.miniapplication.Repository.UserRepository;
import com.example.miniapplication.Service.UserService;
import com.example.miniapplication.dto.UpdateUsernameRequest;
import com.example.miniapplication.dto.UserResponse;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserRepository userRepo;
    private final UserService userService;

    public UserController(UserRepository userRepo, UserService userService) {
        this.userRepo = userRepo;
        this.userService = userService;
    }

    @GetMapping("/me")
    public UserResponse me(Authentication auth) {
        String email = auth.getName(); // safest + consistent
        User u = userRepo.findByEmail(email).orElseThrow();

        return new UserResponse(
                u.getId(),
                u.getUsername(),
                u.getEmail(),
                u.getFirstName(),
                u.getLastName()
        );
    }

    @PatchMapping("/me/username")
    public ResponseEntity<UserResponse> updateUsername(
            Authentication auth,
            @Valid @RequestBody UpdateUsernameRequest req
    ) {
        String email = auth.getName(); // FIX: don't cast principal
        User updated = userService.updateUsername(email, req.username);

        return ResponseEntity.ok(
                new UserResponse(
                        updated.getId(),
                        updated.getUsername(),
                        updated.getEmail(),
                        updated.getFirstName(),
                        updated.getLastName()
                )
        );
    }
}
