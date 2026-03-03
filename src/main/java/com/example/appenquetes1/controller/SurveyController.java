package com.example.appenquetes1.controller;

import com.example.appenquetes1.dto.survey.SurveyResponseDTO;
import com.example.appenquetes1.entity.Survey;
import com.example.appenquetes1.mapper.SurveyMapper;
import com.example.appenquetes1.service.SurveyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/survey")
@CrossOrigin
public class SurveyController {

    @Autowired
    private SurveyService service;

    @PostMapping
    public Survey create(@RequestBody Survey survey) {
        return service.save(survey);
    }

    @GetMapping
    public List<Survey> getAllSurveys() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public Survey getById(@PathVariable Integer id) {
        return service.findById(id);
    }

    // UPDATE
    @PutMapping("/{id}")
    public Survey update(@PathVariable Integer id,
                         @RequestBody Survey survey) {
        survey.setId(id);
        return service.save(survey);
    }

    // DELETE
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

