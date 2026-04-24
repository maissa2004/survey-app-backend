package com.example.appenquetes1.service;

import com.example.appenquetes1.dto.survey.AssignedSurveyDTO;
import com.example.appenquetes1.entity.*;
import com.example.appenquetes1.repository.SessionEnqueteurRepository;
import com.example.appenquetes1.repository.SurveyRepository;
import com.example.appenquetes1.repository.SurveySubmissionRepository;
import com.example.appenquetes1.entity.*;
import com.example.appenquetes1.repository.*;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
public class SurveyService {

    @Autowired
    private EntityManager entityManager;

    @Autowired
    private SurveyRepository repository;

    @Autowired
    private SessionEnqueteurRepository sessionEnqueteurRepository;

    @Autowired
    private SurveySubmissionRepository submissionRepository;
    @Autowired
    private SectionRepository sectionRepository;

    @Autowired
    private SectionQuestionRepository sectionQuestionRepository;

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private QuestionAnswersRepository questionAnswersRepository;

    @Autowired
    private NmAnswersRepository nmAnswersRepository;

    public Survey save(Survey s) {
        return repository.save(s);
    }

    public List<Survey> findAll() {
        return repository.findAll();
    }

    public Survey findById(Integer id) {
        return repository.findById(id).orElse(null);
    }


    @Transactional
    public void delete(Integer id) {
        Survey survey = findById(id);
        if (survey == null) {
            throw new RuntimeException("Survey non trouvé avec ID: " + id);
        }

        try {
            // 1. Désactiver les contraintes de clés étrangères
            entityManager.createNativeQuery("SET FOREIGN_KEY_CHECKS = 0").executeUpdate();

            // 2. Supprimer les nm_answers
            entityManager.createNativeQuery(
                    "DELETE FROM nm_answers WHERE id IN (" +
                            "SELECT id_nm_answers FROM question_answers WHERE id_question IN (" +
                            "SELECT id_question FROM section_question WHERE id_section IN (" +
                            "SELECT id FROM section WHERE id_survey = ?)))"
            ).setParameter(1, id).executeUpdate();

            // 3. Supprimer les question_answers
            entityManager.createNativeQuery(
                    "DELETE FROM question_answers WHERE id_question IN (" +
                            "SELECT id_question FROM section_question WHERE id_section IN (" +
                            "SELECT id FROM section WHERE id_survey = ?))"
            ).setParameter(1, id).executeUpdate();

            // 4. Supprimer les questions
            entityManager.createNativeQuery(
                    "DELETE FROM question WHERE id IN (" +
                            "SELECT id_question FROM section_question WHERE id_section IN (" +
                            "SELECT id FROM section WHERE id_survey = ?))"
            ).setParameter(1, id).executeUpdate();

            // 5. Supprimer les section_question
            entityManager.createNativeQuery(
                    "DELETE FROM section_question WHERE id_section IN (" +
                            "SELECT id FROM section WHERE id_survey = ?)"
            ).setParameter(1, id).executeUpdate();

            // 6. Supprimer les sections
            entityManager.createNativeQuery("DELETE FROM section WHERE id_survey = ?")
                    .setParameter(1, id).executeUpdate();

            // 7. Supprimer le survey
            entityManager.createNativeQuery("DELETE FROM survey WHERE id = ?")
                    .setParameter(1, id).executeUpdate();

            // 8. Réactiver les contraintes
            entityManager.createNativeQuery("SET FOREIGN_KEY_CHECKS = 1").executeUpdate();

            System.out.println("✅ Survey ID " + id + " supprimé avec succès");

        } catch (Exception e) {
            entityManager.createNativeQuery("SET FOREIGN_KEY_CHECKS = 1").executeUpdate();
            throw new RuntimeException("Erreur lors de la suppression: " + e.getMessage());
        }
    }

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
                if (q != null) {
                    q.getAnswers().size();
                    System.out.println("Question: " + q.getCode() + ", Answers: " + q.getAnswers().size());
                }
            });
        });

        return survey;
    }

}

