package com.example.appenquetes1.service;

import com.example.appenquetes1.dto.survey.AssignedSurveyDTO;
import com.example.appenquetes1.entity.*;
import com.example.appenquetes1.repository.SessionEnqueteurRepository;
import com.example.appenquetes1.repository.SurveyRepository;
import com.example.appenquetes1.repository.SurveySubmissionRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
public class SurveyService {

    @Autowired
    private SurveyRepository repository;

    @Autowired
    private SessionEnqueteurRepository sessionEnqueteurRepository;

    @Autowired
    private SurveySubmissionRepository submissionRepository;
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

    public List<AssignedSurveyDTO> getAssignedSurveysForUser(Integer userId) {
        List<SessionEnqueteur> affectations = sessionEnqueteurRepository.findByIdUser(userId);
        Map<Integer, AssignedSurveyDTO> map = new LinkedHashMap<>();

        for (SessionEnqueteur se : affectations) {
            SessionSurvey ss = se.getSessionSurvey();
            if (ss == null) continue;
                Survey survey = ss.getSurvey();
                Session session = ss.getSession();
            if (survey == null || session == null)
                continue;

            AssignedSurveyDTO dto = map.computeIfAbsent(
               survey.getId(), k -> {
                AssignedSurveyDTO d = new AssignedSurveyDTO();
                d.setSurveyId(survey.getId());
                d.setSurveyLibelle(survey.getLibelle());
                d.setSurveyLibelleEn(survey.getLibelleEn());
                d.setSessionName(session.getIntitule());
                d.setSessionStartDate(session.getDateDebut().toLocalDate());
                d.setSessionEndDate(session.getDateFin().toLocalDate());
                return d;
            });
        }

        for (AssignedSurveyDTO dto : map.values()) {
            long count = submissionRepository.countByUserIdAndSurveyId(userId, dto.getSurveyId());
            dto.setSubmissionCount(count);
        }

        return new ArrayList<>(map.values());
    }
    public Survey getSurveyWithSections(Integer surveyId) {

        return repository.findSurveyWithSections(surveyId)
                .orElseThrow(() -> new RuntimeException("Survey introuvable"));
    }

    @Transactional
    public Survey getFullSurvey(Integer id) {
        //  Définir la variable survey
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

