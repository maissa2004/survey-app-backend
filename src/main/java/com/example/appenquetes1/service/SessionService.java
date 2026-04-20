package com.example.appenquetes1.service;

import com.example.appenquetes1.dto.session.SessionRequestDTO;
import com.example.appenquetes1.dto.session.SessionResponseDTO;
import com.example.appenquetes1.entity.Session;
import com.example.appenquetes1.entity.SessionSurvey;
import com.example.appenquetes1.entity.Survey;
import com.example.appenquetes1.mapper.SessionMapper;
import com.example.appenquetes1.repository.SessionRepository;
import com.example.appenquetes1.repository.SessionSurveyRepository;
import com.example.appenquetes1.repository.SurveyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SessionService {

    @Autowired
    private SessionRepository sessionRepository;

    @Autowired
    private SessionSurveyRepository sessionSurveyRepository;

    @Autowired
    private SurveyRepository surveyRepository;

    // Dans SessionService.java, méthode createSession
    @Transactional
    public SessionResponseDTO createSession(SessionRequestDTO request) {
        // Vérifier que les surveys existent
        if (request.getIdSurveys() == null || request.getIdSurveys().isEmpty()) {
            throw new RuntimeException("Au moins un survey est requis");
        }

        for (Integer surveyId : request.getIdSurveys()) {
            if (!surveyRepository.existsById(surveyId)) {
                throw new RuntimeException("Survey non trouvé avec l'id: " + surveyId);
            }
        }

        // Vérifier que la date de fin est après la date de début
        if (request.getDateFin().isBefore(request.getDateDebut())) {
            throw new RuntimeException("La date de fin doit être après la date de début");
        }

        // Créer la session
        Session session = SessionMapper.toEntity(request);
        Session savedSession = sessionRepository.save(session);

        // Ajouter les relations avec les surveys
        for (Integer surveyId : request.getIdSurveys()) {
            SessionSurvey sessionSurvey = new SessionSurvey(savedSession.getId(), surveyId);
            sessionSurveyRepository.save(sessionSurvey);
        }

        // Recharger la session avec les relations
        Session completeSession = sessionRepository.findById(savedSession.getId()).orElse(null);
        return SessionMapper.toDTO(completeSession);
    }

    @Transactional
    public SessionResponseDTO updateSession(Integer id, SessionRequestDTO request) {
        Session existing = sessionRepository.findById(id).orElse(null);
        if (existing == null) return null;

        // Mettre à jour les champs
        if (request.getIntitule() != null) existing.setIntitule(request.getIntitule());
        if (request.getDateDebut() != null) existing.setDateDebut(request.getDateDebut());
        if (request.getDateFin() != null) existing.setDateFin(request.getDateFin());
        if (request.getStatus() != null) existing.setStatus(request.getStatus());

        Session saved = sessionRepository.save(existing);

        // Mettre à jour les surveys associés
        if (request.getIdSurveys() != null && !request.getIdSurveys().isEmpty()) {
            // Supprimer les anciennes relations
            sessionSurveyRepository.deleteBySessionId(id);

            // Ajouter les nouvelles relations
            for (Integer surveyId : request.getIdSurveys()) {
                SessionSurvey sessionSurvey = new SessionSurvey(id, surveyId);
                sessionSurveyRepository.save(sessionSurvey);
            }
        }

        // Recharger la session avec les relations
        Session completeSession = sessionRepository.findById(id).orElse(null);
        return SessionMapper.toDTO(completeSession);
    }

    public List<SessionResponseDTO> getAllSessions() {
        return sessionRepository.findAll().stream()
                .map(SessionMapper::toDTO)
                .collect(Collectors.toList());
    }

    public SessionResponseDTO getSessionById(Integer id) {
        Session session = sessionRepository.findById(id).orElse(null);
        return session != null ? SessionMapper.toDTO(session) : null;
    }

    @Transactional
    public void deleteSession(Integer id) {
        sessionSurveyRepository.deleteBySessionId(id);
        sessionRepository.deleteById(id);
    }

    @Transactional
    public boolean activateSession(Integer id) {
        Session session = sessionRepository.findById(id).orElse(null);
        if (session == null) return false;

        LocalDateTime now = LocalDateTime.now();
        if (now.isBefore(session.getDateDebut()) || now.isAfter(session.getDateFin())) {
            throw new RuntimeException("Impossible d'activer : la session n'est pas dans sa période");
        }

        session.setStatus(Session.Status.active);
        sessionRepository.save(session);
        return true;
    }

    @Transactional
    public boolean deactivateSession(Integer id) {
        Session session = sessionRepository.findById(id).orElse(null);
        if (session == null) return false;

        session.setStatus(Session.Status.inactive);
        sessionRepository.save(session);
        return true;
    }

    public List<SessionResponseDTO> getSessionsByStatus(Session.Status status) {
        return sessionRepository.findByStatus(status).stream()
                .map(SessionMapper::toDTO)
                .collect(Collectors.toList());
    }
}