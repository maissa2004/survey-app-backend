// com.example.appenquetes1.controller.SessionEnqueteurController.java
package com.example.appenquetes1.controller;

import com.example.appenquetes1.dto.session.SessionEnqueteurRequestDTO;
import com.example.appenquetes1.dto.session.SessionEnqueteurResponseDTO;
import com.example.appenquetes1.entity.User;
import com.example.appenquetes1.service.SessionEnqueteurService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/sessions/enqueteurs")
//@CrossOrigin(origins = "http://localhost:4200")
public class SessionEnqueteurController {

    @Autowired
    private SessionEnqueteurService service;

    // Récupérer les enquêteurs par session_survey
    @GetMapping("/session-survey/{sessionSurveyId}")
    public ResponseEntity<List<SessionEnqueteurResponseDTO>> getEnqueteursBySessionSurvey(
            @PathVariable Integer sessionSurveyId) {
        return ResponseEntity.ok(service.getEnqueteursBySessionSurvey(sessionSurveyId));
    }

    // Récupérer les enquêteurs par session
    @GetMapping("/session/{sessionId}")
    public ResponseEntity<List<SessionEnqueteurResponseDTO>> getEnqueteursBySession(
            @PathVariable Integer sessionId) {
        return ResponseEntity.ok(service.getEnqueteursBySession(sessionId));
    }

    // Récupérer les enquêteurs disponibles pour un session_survey
    @GetMapping("/session-survey/{sessionSurveyId}/available")
    public ResponseEntity<List<User>> getAvailableEnqueteurs(
            @PathVariable Integer sessionSurveyId) {
        return ResponseEntity.ok(service.getAvailableEnqueteursForSessionSurvey(sessionSurveyId));
    }

    // Affecter un enquêteur
    @PostMapping("/assign")
    public ResponseEntity<?> assignEnqueteur(@RequestBody SessionEnqueteurRequestDTO request) {
        System.out.println("=== ASSIGN ENQUETEUR ===");
        System.out.println("idSessionSurvey: " + request.getIdSessionSurvey());
        System.out.println("idUser: " + request.getIdUser());
        try {
            SessionEnqueteurResponseDTO result = service.assignEnqueteur(request);
            return ResponseEntity.ok(result);
        } catch (RuntimeException e) {
            System.err.println("Erreur: " + e.getMessage());
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // Supprimer une affectation
    @DeleteMapping("/session-survey/{sessionSurveyId}/user/{userId}")
    public ResponseEntity<Void> removeEnqueteur(
            @PathVariable Integer sessionSurveyId,
            @PathVariable Integer userId) {
        try {
            service.removeEnqueteur(sessionSurveyId, userId);
            return ResponseEntity.ok().build();
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }
}