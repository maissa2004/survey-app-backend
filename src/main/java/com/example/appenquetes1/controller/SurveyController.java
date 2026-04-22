package com.example.appenquetes1.controller;

import com.example.appenquetes1.dto.survey.AssignedSurveyDTO;
import com.example.appenquetes1.dto.survey.SurveyCreateDTO;
import com.example.appenquetes1.dto.survey.SurveyResponseDTO;
import com.example.appenquetes1.entity.EtatSurvey;
import com.example.appenquetes1.entity.Survey;
import com.example.appenquetes1.mapper.SurveyMapper;
import com.example.appenquetes1.service.SurveyService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/survey")
//@CrossOrigin(origins = {"http://localhost:4200", "*"})

public class SurveyController {

    @Autowired
    private SurveyService service;

    @PostMapping
    public Survey create(@RequestBody SurveyCreateDTO createDTO) {
        System.out.println("📥 Création survey - idEtatSurvey: " + createDTO.getIdEtatSurvey());

        Survey survey = new Survey();
        survey.setCode(createDTO.getCode());
        survey.setLibelle(createDTO.getLibelle());
        survey.setLibelleEn(createDTO.getLibelleEn());
        survey.setFormReference(createDTO.isFormReference());

        // Gérer l'état
        if (createDTO.getIdEtatSurvey() != null) {
            EtatSurvey etat = new EtatSurvey();
            etat.setid(createDTO.getIdEtatSurvey());
            survey.setEtatSurvey(etat);
        }

        return service.save(survey);
    }

    @GetMapping
    public List<SurveyResponseDTO> getAllSurveys() {
        List<Survey> surveys = service.findAll();
        return surveys.stream()
                .map(SurveyMapper::toDTO)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<SurveyResponseDTO> getById(@PathVariable Integer id) {
        Survey survey = service.findById(id);
        if (survey == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(SurveyMapper.toDTO(survey));
    }

    // UPDATE
    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Integer id, @RequestBody SurveyCreateDTO updateDTO) {
        System.out.println("📥 Mise à jour survey ID: " + id);
        System.out.println("📥 idEtatSurvey reçu: " + updateDTO.getIdEtatSurvey());

        // 1. Récupérer le survey existant
        Survey survey = service.findById(id);
        if (survey == null) {
            System.out.println("❌ Survey non trouvé avec ID: " + id);
            return ResponseEntity.notFound().build();
        }

        // 2. Mettre à jour les champs
        survey.setCode(updateDTO.getCode());
        survey.setLibelle(updateDTO.getLibelle());
        survey.setLibelleEn(updateDTO.getLibelleEn());
        survey.setFormReference(updateDTO.isFormReference());

        // 3. Gérer l'état
        if (updateDTO.getIdEtatSurvey() != null) {
            EtatSurvey etat = new EtatSurvey();
            etat.setid(updateDTO.getIdEtatSurvey());
            survey.setEtatSurvey(etat);
        }

        // 4. Sauvegarder
        Survey saved = service.save(survey);
        System.out.println("✅ Survey mis à jour avec succès: " + saved.getId());

        return ResponseEntity.ok(saved);
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

    @GetMapping("/assigned")
    public ResponseEntity<List<AssignedSurveyDTO>> getAssignedSurveys(HttpServletRequest request) {
        Integer userId = (Integer) request.getAttribute("userId");
        if (userId == null) {
            return ResponseEntity.status(401).build();
        }
        return ResponseEntity.ok(service.getAssignedSurveysForUser(userId));
    }


}

