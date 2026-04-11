// com.example.appenquetes1.service.UserService.java
package com.example.appenquetes1.service;

import com.example.appenquetes1.config.JwtUtil;
import com.example.appenquetes1.dto.auth.AuthResponse;
import com.example.appenquetes1.dto.auth.LoginRequest;
import com.example.appenquetes1.dto.auth.RegisterRequest;
import com.example.appenquetes1.dto.user.UserResponseDTO;
import com.example.appenquetes1.dto.user.UserUpdateDTO;
import com.example.appenquetes1.entity.User;
import com.example.appenquetes1.mapper.UserMapper;
import com.example.appenquetes1.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtil jwtUtil;

    public AuthResponse register(RegisterRequest request) {
        if (userRepository.existsByUsername(request.getUsername())) {
            throw new RuntimeException("Nom d'utilisateur déjà pris");
        }
        if (request.getEmail() != null && !request.getEmail().isEmpty() && userRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Email déjà utilisé");
        }
        if (request.getPhone() != null && !request.getPhone().isEmpty() && userRepository.existsByPhone(request.getPhone())) {
            throw new RuntimeException("Téléphone déjà utilisé");
        }

        User user = new User(
                request.getUsername(),
                request.getEmail(),
                request.getPhone(),
                passwordEncoder.encode(request.getPassword()),
                request.getRole() != null ? request.getRole() : User.Role.admin
        );

        User savedUser = userRepository.save(user);
        String token = jwtUtil.generateToken(savedUser.getUsername(), savedUser.getRole().name(), savedUser.getId());

        return new AuthResponse(token, savedUser.getUsername(), savedUser.getRole().name(),
                savedUser.getId(), savedUser.getEmail(), savedUser.getPhone());
    }

    public AuthResponse login(LoginRequest request) {
        System.out.println("=== LOGIN DEBUG ===");
        System.out.println("Login reçu: " + request.getLogin());
        System.out.println("Password reçu: " + request.getPassword());

        // Test direct par username
        Optional<User> byUsername = userRepository.findByUsername(request.getLogin());
        System.out.println("Recherche par username: " + byUsername.isPresent());

        // Test direct par email
        Optional<User> byEmail = userRepository.findByEmail(request.getLogin());
        System.out.println("Recherche par email: " + byEmail.isPresent());

        // Test direct par phone
        Optional<User> byPhone = userRepository.findByPhone(request.getLogin());
        System.out.println("Recherche par phone: " + byPhone.isPresent());

        // Test par la méthode générique
        Optional<User> userOpt = userRepository.findByUsernameEmailOrPhone(request.getLogin());
        System.out.println("Recherche par méthode générique: " + userOpt.isPresent());

        if (userOpt.isEmpty()) {
            throw new RuntimeException("Utilisateur non trouvé");
        }

        User user = userOpt.get();
        System.out.println("Utilisateur trouvé: " + user.getUsername());

        boolean passwordMatches = passwordEncoder.matches(request.getPassword(), user.getPasswordHash());
        System.out.println("Password matches: " + passwordMatches);

        if (!user.getIsActive()) {
            throw new RuntimeException("Compte désactivé");
        }

        if (!passwordMatches) {
            throw new RuntimeException("Mot de passe incorrect");
        }

        user.setLastLogin(LocalDateTime.now());
        userRepository.save(user);

        String token = jwtUtil.generateToken(user.getUsername(), user.getRole().name(), user.getId());

        return new AuthResponse(token, user.getUsername(), user.getRole().name(),
                user.getId(), user.getEmail(), user.getPhone());
    }

    public UserResponseDTO findById(Integer id) {
        User user = userRepository.findById(id).orElse(null);
        return user != null ? UserMapper.toDTO(user) : null;
    }

    public UserResponseDTO findByUsername(String username) {
        User user = userRepository.findByUsername(username).orElse(null);
        return user != null ? UserMapper.toDTO(user) : null;
    }

    public List<UserResponseDTO> findAll() {
        return userRepository.findAll().stream()
                .map(UserMapper::toDTO)
                .collect(Collectors.toList());
    }

    public UserResponseDTO updateUser(Integer id, UserUpdateDTO userData) {
        User existing = userRepository.findById(id).orElse(null);
        if (existing == null) return null;

        if (userData.getEmail() != null) existing.setEmail(userData.getEmail());
        if (userData.getPhone() != null) existing.setPhone(userData.getPhone());
        if (userData.getRole() != null) existing.setRole(userData.getRole());
        if (userData.getIsActive() != null) existing.setIsActive(userData.getIsActive());

        User saved = userRepository.save(existing);
        return UserMapper.toDTO(saved);
    }

    public void deleteUser(Integer id) {
        userRepository.deleteById(id);
    }

    public boolean changePassword(Integer userId, String oldPassword, String newPassword) {
        User user = userRepository.findById(userId).orElse(null);
        if (user == null) return false;

        if (!passwordEncoder.matches(oldPassword, user.getPasswordHash())) {
            return false;
        }

        user.setPasswordHash(passwordEncoder.encode(newPassword));
        userRepository.save(user);
        return true;
    }

    // NOUVELLE MÉTHODE : Récupère l'entité User brute (non mappée en DTO)
    public User getRawUserByUsername(String username) {
        return userRepository.findByUsername(username).orElse(null);
    }
}