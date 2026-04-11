package com.example.appenquetes1.service;

import com.example.appenquetes1.dto.session.SessionRequestDTO;
import com.example.appenquetes1.dto.session.SessionResponseDTO;
import com.example.appenquetes1.entity.Session;
import com.example.appenquetes1.entity.Survey;
import com.example.appenquetes1.mapper.SessionMapper;
import com.example.appenquetes1.repository.SessionRepository;
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
    private SurveyRepository surveyRepository;

    // US26 : Créer une session d'enquête
    public SessionResponseDTO createSession(SessionRequestDTO request) {
        // Vérifier que le survey existe
        Survey survey = surveyRepository.findById(request.getIdSurvey()).orElse(null);
        if (survey == null) {
            throw new RuntimeException("Survey non trouvé avec l'id: " + request.getIdSurvey());
        }

        // Vérifier que la date de fin est après la date de début
        if (request.getDateFin().isBefore(request.getDateDebut())) {
            throw new RuntimeException("La date de fin doit être après la date de début");
        }

        // Vérifier les chevauchements avec d'autres sessions du même survey
        List<Session> overlapping = sessionRepository.findOverlappingSessions(
                request.getIdSurvey(), request.getDateDebut(), request.getDateFin());
        if (!overlapping.isEmpty()) {
            throw new RuntimeException("Une session existe déjà sur cette période pour ce survey");
        }

        Session session = SessionMapper.toEntity(request);
        Session saved = sessionRepository.save(session);
        return SessionMapper.toDTO(saved);
    }

    // Récupérer toutes les sessions
    public List<SessionResponseDTO> getAllSessions() {
        return sessionRepository.findAll().stream()
                .map(SessionMapper::toDTO)
                .collect(Collectors.toList());
    }

    // Récupérer une session par ID
    public SessionResponseDTO getSessionById(Integer id) {
        Session session = sessionRepository.findById(id).orElse(null);
        return session != null ? SessionMapper.toDTO(session) : null;
    }

    // US28 : Modifier une session (période)
    public SessionResponseDTO updateSession(Integer id, SessionRequestDTO request) {
        Session existing = sessionRepository.findById(id).orElse(null);
        if (existing == null) return null;

        if (request.getIntitule() != null) existing.setIntitule(request.getIntitule());
        if (request.getDateDebut() != null) existing.setDateDebut(request.getDateDebut());
        if (request.getDateFin() != null) existing.setDateFin(request.getDateFin());
        if (request.getIdSurvey() != null) existing.setIdSurvey(request.getIdSurvey());

        Session saved = sessionRepository.save(existing);
        return SessionMapper.toDTO(saved);
    }

    // Supprimer une session
    public void deleteSession(Integer id) {
        sessionRepository.deleteById(id);
    }

    // US29 : Activer une session
    @Transactional
    public boolean activateSession(Integer id) {
        Session session = sessionRepository.findById(id).orElse(null);
        if (session == null) return false;

        // Vérifier que la date actuelle est dans la période
        LocalDateTime now = LocalDateTime.now();
        if (now.isBefore(session.getDateDebut()) || now.isAfter(session.getDateFin())) {
            throw new RuntimeException("Impossible d'activer : la session n'est pas dans sa période");
        }

        int updated = sessionRepository.activateSession(id);
        return updated > 0;
    }

    // US29 : Désactiver une session
    @Transactional
    public boolean deactivateSession(Integer id) {
        int updated = sessionRepository.deactivateSession(id);
        return updated > 0;
    }

    // Sessions par statut
    public List<SessionResponseDTO> getSessionsByStatus(Session.Status status) {
        return sessionRepository.findByStatus(status).stream()
                .map(SessionMapper::toDTO)
                .collect(Collectors.toList());
    }

    // Sessions par survey (US27)
    public List<SessionResponseDTO> getSessionsBySurvey(Integer surveyId) {
        return sessionRepository.findByIdSurvey(surveyId).stream()
                .map(SessionMapper::toDTO)
                .collect(Collectors.toList());
    }

    // Sessions actives actuellement
    public List<SessionResponseDTO> getCurrentActiveSessions() {
        sessionRepository.autoFinishExpiredSessions();
        return sessionRepository.findCurrentActiveSessions().stream()
                .map(SessionMapper::toDTO)
                .collect(Collectors.toList());
    }

    // Sessions à venir
    public List<SessionResponseDTO> getUpcomingSessions() {
        return sessionRepository.findUpcomingSessions().stream()
                .map(SessionMapper::toDTO)
                .collect(Collectors.toList());
    }

    // Sessions terminées
    public List<SessionResponseDTO> getFinishedSessions() {
        return sessionRepository.findFinishedSessions().stream()
                .map(SessionMapper::toDTO)
                .collect(Collectors.toList());
    }

    // Mettre à jour automatiquement les statuts
    @Transactional
    public void updateSessionStatuses() {
        sessionRepository.autoFinishExpiredSessions();
    }
}