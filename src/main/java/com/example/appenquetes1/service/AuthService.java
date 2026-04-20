package com.example.appenquetes1.service;
import com.example.appenquetes1.config.JwtUtil;

import com.example.appenquetes1.dto.auth.LoginResponseDTO;
import com.example.appenquetes1.dto.auth.RegisterRequestDTO;
import com.example.appenquetes1.dto.auth.RegisterResponseDTO;
import com.example.appenquetes1.entity.User;
import com.example.appenquetes1.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtUtil jwtUtil;

    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public LoginResponseDTO login(String username, String password) {
        Optional<User> userOpt = userRepository.findByUsername(username);
        User user =userOpt.get();
        if (userOpt.isPresent() && passwordEncoder.matches(password, user.getPasswordHash())) {
            if (user.getIsActive() != true) {
                return null; // Compte désactivé
            }
            user.setLastLogin(LocalDateTime.now());
            userRepository.save(user);

            // Génération du token JWT
            String token = jwtUtil.generateToken(
                    user.getUsername(),
                    user.getRole().name(),
                    user.getId()
            );
            LoginResponseDTO response = new LoginResponseDTO();
            response.setUserId(user.getId());
            response.setUsername(user.getUsername());
            response.setRole(user.getRole().name());
            //response.setToken("temp-token-" + System.currentTimeMillis());
            response.setToken(token);
            return response;
        }

        return null;
    }

    public RegisterResponseDTO register(RegisterRequestDTO request) {
        if (userRepository.findByUsername(request.getUsername()).isPresent()) {
            return new RegisterResponseDTO(null, null, "Username existe deje");
        }
        if (request.getEmail() != null && !request.getEmail().isEmpty()
                && userRepository.existsByEmail(request.getEmail())) {
            return new RegisterResponseDTO(null, null, "Email existe deja");
        }
        if (request.getPhone() != null && !request.getPhone().isEmpty()
                && userRepository.existsByPhone(request.getPhone())) {
            return new RegisterResponseDTO(null, null, "Phone exite deja ");
        }

        User newUser = new User();
        newUser.setUsername(request.getUsername());
        newUser.setEmail(request.getEmail());
        newUser.setPhone(request.getPhone() != null ? request.getPhone() : null);
        newUser.setPasswordHash(passwordEncoder.encode(request.getPassword()));

        newUser.setRole(User.Role.enqueteur);
        newUser.setIsActive(true);
        newUser.setCreatedAt(LocalDateTime.now());
        newUser.setUpdatedAt(LocalDateTime.now());
        newUser.setLastLogin(LocalDateTime.now());

        User savedUser = userRepository.save(newUser);

        RegisterResponseDTO response = new RegisterResponseDTO();
        response.setUserId(savedUser.getId());
        response.setUsername(savedUser.getUsername());
        response.setMessage("Inscription réussie");

        return response;
    }
}