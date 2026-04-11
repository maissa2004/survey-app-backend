// com.example.appenquetes1.controller.UserController.java
package com.example.appenquetes1.controller;

import com.example.appenquetes1.dto.auth.AuthResponse;
import com.example.appenquetes1.dto.auth.LoginRequest;
import com.example.appenquetes1.dto.auth.RegisterRequest;
import com.example.appenquetes1.dto.user.UserResponseDTO;
import com.example.appenquetes1.dto.user.UserUpdateDTO;
import com.example.appenquetes1.entity.User;
import com.example.appenquetes1.repository.UserRepository;
import com.example.appenquetes1.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "http://localhost:4200")
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UserRepository userRepository;


    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest request) {
        try {
            AuthResponse response = userService.register(request);
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        try {
            AuthResponse response = userService.login(request);
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/users")
    public ResponseEntity<List<UserResponseDTO>> getAllUsers() {
        return ResponseEntity.ok(userService.findAll());
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<UserResponseDTO> getUserById(@PathVariable Integer id) {
        UserResponseDTO user = userService.findById(id);
        return user != null ? ResponseEntity.ok(user) : ResponseEntity.notFound().build();
    }

    @GetMapping("/test-password")
    public ResponseEntity<?> testPassword() {
        User admin = userService.getRawUserByUsername("admin");
        if (admin == null) {
            return ResponseEntity.ok("❌ Admin non trouvé dans la base");
        }

        String rawPassword = "admin123";
        String hashedPassword = admin.getPasswordHash();
        boolean matches = passwordEncoder.matches(rawPassword, hashedPassword);

        return ResponseEntity.ok(String.format(
                "Utilisateur: %s\nPassword hash: %s\nMatches: %s",
                admin.getUsername(), hashedPassword, matches
        ));
    }

    // Dans UserController.java
    @GetMapping("/test-direct")
    public ResponseEntity<?> testDirect() {
        Optional<User> byUsername = userRepository.findByUsername("admin");
        if (byUsername.isPresent()) {
            User user = byUsername.get();
            return ResponseEntity.ok("Utilisateur trouvé: " + user.getUsername() +
                    ", password hash: " + user.getPasswordHash());
        }
        return ResponseEntity.ok("Utilisateur non trouvé");
    }

    @PutMapping("/users/{id}")
    public ResponseEntity<UserResponseDTO> updateUser(@PathVariable Integer id, @RequestBody UserUpdateDTO user) {
        UserResponseDTO updated = userService.updateUser(id, user);
        return updated != null ? ResponseEntity.ok(updated) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Integer id) {
        userService.deleteUser(id);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/change-password/{userId}")
    public ResponseEntity<?> changePassword(@PathVariable Integer userId,
                                            @RequestParam String oldPassword,
                                            @RequestParam String newPassword) {
        boolean changed = userService.changePassword(userId, oldPassword, newPassword);
        return changed ? ResponseEntity.ok().build() : ResponseEntity.badRequest().body("Mot de passe incorrect");
    }
}