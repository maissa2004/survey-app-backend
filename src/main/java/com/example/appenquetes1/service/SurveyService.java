package com.example.appenquetes1.service;

import com.example.appenquetes1.entity.EtatSurvey;
import com.example.appenquetes1.entity.Question;
import com.example.appenquetes1.entity.Section;
import com.example.appenquetes1.entity.Survey;
import com.example.appenquetes1.repository.SurveyRepository;
import jakarta.transaction.Transactional;
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

    @Transactional
    public Survey getFullSurvey(Integer id) {
        // 🔥 CORRECTION : Définir la variable survey
        Survey survey = repository.findFullSurvey(id)
                .orElseThrow(() -> new RuntimeException("Survey introuvable"));

        // Forcer le chargement des réponses
        survey.getSections().forEach(section -> {
            section.getSectionQuestions().forEach(sq -> {
                Question q = sq.getQuestion();
                // Forcer l'initialisation des réponses
                q.getAnswers().size();  // Force le chargement
                System.out.println("Question: " + q.getCode() + ", Answers: " + q.getAnswers().size());
            });
        });

        return survey;
    }


}

