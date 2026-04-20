package com.example.appenquetes1.controller;

import com.example.appenquetes1.dto.auth.LoginRequest;
import com.example.appenquetes1.dto.auth.LoginResponseDTO;
import com.example.appenquetes1.dto.auth.RegisterRequestDTO;
import com.example.appenquetes1.dto.auth.RegisterResponseDTO;
import com.example.appenquetes1.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
@RestController
@RequestMapping("/api/auth")
//@CrossOrigin(origins = "*")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/loginApp")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody LoginRequest request) {
        LoginResponseDTO response = authService.login(request.getLogin(), request.getPassword());
        if (response != null) {
            return ResponseEntity.ok(response);
        }
        return ResponseEntity.status(401).build();
    }

    @PostMapping("/registerApp")
    public ResponseEntity<?> register(@RequestBody RegisterRequestDTO request) {
        RegisterResponseDTO response = authService.register(request);
        if (response.getUserId() != null) {
            return ResponseEntity.ok(response);
        }
        Map<String, String> error = new HashMap<>();
        error.put("error", response.getMessage());
        return ResponseEntity.status(409).body(error);
    }
}
