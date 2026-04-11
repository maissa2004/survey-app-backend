// com.example.appenquetes1.controller.SurveyController.java
package com.example.appenquetes1.controller;

import com.example.appenquetes1.dto.survey.SurveyResponseDTO;
import com.example.appenquetes1.entity.Survey;
import com.example.appenquetes1.mapper.SurveyMapper;
import com.example.appenquetes1.service.SurveyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/survey")
@CrossOrigin(origins = "http://localhost:4200")
public class SurveyController {

    @Autowired
    private SurveyService service;

    @PostMapping
    public Survey create(@RequestBody Survey survey) {
        return service.save(survey);
    }

    // NOUVEAU : Créer un survey avec un utilisateur spécifique
    @PostMapping("/user/{userId}")
    public ResponseEntity<Survey> createForUser(@PathVariable Integer userId, @RequestBody Survey survey) {
        Survey created = service.createSurvey(survey, userId);
        return ResponseEntity.ok(created);
    }

    @GetMapping
    public List<Survey> getAllSurveys() {
        return service.findAll();
    }

    // NOUVEAU : Récupérer les surveys d'un utilisateur
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<SurveyResponseDTO>> getSurveysByUser(@PathVariable Integer userId) {
        List<Survey> surveys = service.getSurveysByUser(userId);
        List<SurveyResponseDTO> dtos = surveys.stream()
                .map(SurveyMapper::toDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }

    // NOUVEAU : Récupérer les surveys actifs d'un utilisateur
    @GetMapping("/user/{userId}/active")
    public ResponseEntity<List<SurveyResponseDTO>> getActiveSurveysByUser(@PathVariable Integer userId) {
        List<Survey> surveys = service.getActiveSurveysByUser(userId);
        List<SurveyResponseDTO> dtos = surveys.stream()
                .map(SurveyMapper::toDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }

    // NOUVEAU : Récupérer les surveys avec détails par utilisateur
    @GetMapping("/user/{userId}/full")
    public ResponseEntity<List<SurveyResponseDTO>> getFullSurveysByUser(@PathVariable Integer userId) {
        List<Survey> surveys = service.getSurveysByUserWithDetails(userId);
        List<SurveyResponseDTO> dtos = surveys.stream()
                .map(SurveyMapper::toDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }

    @GetMapping("/{id}")
    public Survey getById(@PathVariable Integer id) {
        return service.findById(id);
    }

    @PutMapping("/{id}")
    public Survey update(@PathVariable Integer id, @RequestBody Survey survey) {
        survey.setId(id);
        return service.save(survey);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Integer id) {
        service.delete(id);
    }

    @GetMapping("/detail/{id}")
    public Survey getSurvey(@PathVariable Integer id) {
        return service.getSurveyWithSections(id);
    }

    @GetMapping("/full/{id}")
    public SurveyResponseDTO getFullSurvey(@PathVariable Integer id) {
        Survey survey = service.getFullSurvey(id);
        return SurveyMapper.toDTO(survey);
    }
}