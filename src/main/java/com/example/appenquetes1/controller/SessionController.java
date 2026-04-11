package com.example.appenquetes1.controller;

import com.example.appenquetes1.dto.session.SessionRequestDTO;
import com.example.appenquetes1.dto.session.SessionResponseDTO;
import com.example.appenquetes1.entity.Session;
import com.example.appenquetes1.service.SessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/sessions")
@CrossOrigin(origins = "http://localhost:4200")
public class SessionController {

    @Autowired
    private SessionService sessionService;

    @PostMapping
    public ResponseEntity<?> createSession(@RequestBody SessionRequestDTO request) {
        try {
            SessionResponseDTO session = sessionService.createSession(request);
            return ResponseEntity.ok(session);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<List<SessionResponseDTO>> getAllSessions() {
        return ResponseEntity.ok(sessionService.getAllSessions());
    }

    @GetMapping("/{id}")
    public ResponseEntity<SessionResponseDTO> getSessionById(@PathVariable Integer id) {
        SessionResponseDTO session = sessionService.getSessionById(id);
        return session != null ? ResponseEntity.ok(session) : ResponseEntity.notFound().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateSession(@PathVariable Integer id, @RequestBody SessionRequestDTO request) {
        try {
            SessionResponseDTO session = sessionService.updateSession(id, request);
            return session != null ? ResponseEntity.ok(session) : ResponseEntity.notFound().build();
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSession(@PathVariable Integer id) {
        sessionService.deleteSession(id);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{id}/activate")
    public ResponseEntity<?> activateSession(@PathVariable Integer id) {
        try {
            boolean activated = sessionService.activateSession(id);
            return activated ? ResponseEntity.ok().build() : ResponseEntity.badRequest().body("Activation impossible");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/{id}/deactivate")
    public ResponseEntity<?> deactivateSession(@PathVariable Integer id) {
        boolean deactivated = sessionService.deactivateSession(id);
        return deactivated ? ResponseEntity.ok().build() : ResponseEntity.badRequest().body("Désactivation impossible");
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<List<SessionResponseDTO>> getSessionsByStatus(@PathVariable String status) {
        try {
            Session.Status sessionStatus = Session.Status.valueOf(status.toLowerCase());
            return ResponseEntity.ok(sessionService.getSessionsByStatus(sessionStatus));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }
}