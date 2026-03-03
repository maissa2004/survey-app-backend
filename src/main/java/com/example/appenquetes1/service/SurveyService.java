package com.example.appenquetes1.service;

import com.example.appenquetes1.entity.EtatSurvey;
import com.example.appenquetes1.entity.Section;
import com.example.appenquetes1.entity.Survey;
import com.example.appenquetes1.repository.SurveyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SurveyService {

    @Autowired
    private SurveyRepository repository;

    public Survey save(Survey s) {
        return repository.save(s);
    }

    public List<Survey> findAll() {
        return repository.findAll();
    }

    public Survey findById(Integer id) {
        return repository.findById(id).orElse(null);
    }

    public void delete(Integer id) {
        repository.deleteById(id);
    }

    /*public List<Section> findSectionsBySurveyId(Integer surveyId) {

        Survey survey = findById(surveyId);

        return survey.getSections();
    }*/

    public Survey getSurveyWithSections(Integer surveyId) {

        return repository.findSurveyWithSections(surveyId)
                .orElseThrow(() -> new RuntimeException("Survey introuvable"));
    }

    public Survey getFullSurvey(Integer id) {
        return repository.findFullSurvey(id)
                .orElseThrow(() -> new RuntimeException("Survey introuvable"));
    }


}

