package com.example.appenquetes1.service;

import com.example.appenquetes1.dto.session.SessionRequestDTO;
import com.example.appenquetes1.dto.session.SessionResponseDTO;
import com.example.appenquetes1.entity.Session;
import com.example.appenquetes1.entity.SessionSurvey;
import com.example.appenquetes1.mapper.SessionMapper;
import com.example.appenquetes1.repository.SessionEnqueteurRepository;
import com.example.appenquetes1.repository.SessionRepository;
import com.example.appenquetes1.repository.SessionSurveyRepository;
import com.example.appenquetes1.repository.SurveyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class SessionService {

    @Autowired
    private SessionRepository sessionRepository;

    @Autowired
    private SessionSurveyRepository sessionSurveyRepository;

    @Autowired
    private SessionEnqueteurRepository sessionEnqueteurRepository;

    @Autowired
    private SurveyRepository surveyRepository;

    // 🔥 MÉTHODE MANQUANTE - Récupérer toutes les sessions
    public List<SessionResponseDTO> getAllSessions() {
        return sessionRepository.findAll().stream()
                .map(SessionMapper::toDTO)
                .collect(Collectors.toList());
    }

    // 🔥 MÉTHODE MANQUANTE - Récupérer une session par ID
    public SessionResponseDTO getSessionById(Integer id) {
        Session session = sessionRepository.findById(id).orElse(null);
        return session != null ? SessionMapper.toDTO(session) : null;
    }

    // 🔥 MÉTHODE MANQUANTE - Récupérer les sessions par statut
    public List<SessionResponseDTO> getSessionsByStatus(Session.Status status) {
        return sessionRepository.findByStatus(status).stream()
                .map(SessionMapper::toDTO)
                .collect(Collectors.toList());
    }

    // 🔥 MÉTHODE MANQUANTE - Activer une session
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

    // 🔥 MÉTHODE MANQUANTE - Désactiver une session
    @Transactional
    public boolean deactivateSession(Integer id) {
        Session session = sessionRepository.findById(id).orElse(null);
        if (session == null) return false;

        session.setStatus(Session.Status.inactive);
        sessionRepository.save(session);
        return true;
    }

    // 🔥 MÉTHODE MANQUANTE - Supprimer une session
    @Transactional
    public void deleteSession(Integer id) {
        // Supprimer d'abord les enquêteurs liés
        List<SessionSurvey> sessionSurveys = sessionSurveyRepository.findByIdSession(id);
        for (SessionSurvey ss : sessionSurveys) {
            sessionEnqueteurRepository.deleteByIdSessionSurvey(ss.getId());
        }
        // Supprimer les relations session_survey
        sessionSurveyRepository.deleteBySessionId(id);
        // Supprimer la session
        sessionRepository.deleteById(id);
    }

    // 🔥 MÉTHODE EXISTANTE - Créer une session
    @Transactional
    public SessionResponseDTO createSession(SessionRequestDTO request) {
        if (request.getIdSurveys() == null || request.getIdSurveys().isEmpty()) {
            throw new RuntimeException("Au moins un survey est requis");
        }

        for (Integer surveyId : request.getIdSurveys()) {
            if (!surveyRepository.existsById(surveyId)) {
                throw new RuntimeException("Survey non trouvé avec l'id: " + surveyId);
            }
        }

        if (request.getDateFin().isBefore(request.getDateDebut())) {
            throw new RuntimeException("La date de fin doit être après la date de début");
        }

        Session session = SessionMapper.toEntity(request);
        Session savedSession = sessionRepository.save(session);

        for (Integer surveyId : request.getIdSurveys()) {
            SessionSurvey sessionSurvey = new SessionSurvey(savedSession.getId(), surveyId);
            sessionSurveyRepository.save(sessionSurvey);
        }

        Session completeSession = sessionRepository.findById(savedSession.getId()).orElse(null);
        return SessionMapper.toDTO(completeSession);
    }

    @Transactional
    public SessionResponseDTO updateSession(Integer id, SessionRequestDTO request) {
        Session existing = sessionRepository.findById(id).orElse(null);
        if (existing == null) return null;

        // Mettre à jour les champs de la session
        if (request.getIntitule() != null) existing.setIntitule(request.getIntitule());
        if (request.getDateDebut() != null) existing.setDateDebut(request.getDateDebut());
        if (request.getDateFin() != null) existing.setDateFin(request.getDateFin());
        if (request.getStatus() != null) existing.setStatus(request.getStatus());

        Session saved = sessionRepository.save(existing);

        // 🔥 GESTION CORRECTE DES SURVEYS
        if (request.getIdSurveys() != null) {
            // Récupérer les relations actuelles
            List<SessionSurvey> currentRelations = sessionSurveyRepository.findByIdSession(id);
            List<Integer> currentSurveyIds = currentRelations.stream()
                    .map(SessionSurvey::getIdSurvey)
                    .collect(Collectors.toList());

            List<Integer> newSurveyIds = request.getIdSurveys();

            System.out.println("=== MISE À JOUR SESSION ===");
            System.out.println("Session ID: " + id);
            System.out.println("Surveys actuels: " + currentSurveyIds);
            System.out.println("Nouveaux surveys: " + newSurveyIds);

            // 🔥 1. Supprimer les relations qui ne sont plus dans la nouvelle liste
            for (SessionSurvey relation : currentRelations) {
                if (!newSurveyIds.contains(relation.getIdSurvey())) {
                    System.out.println("🗑️ Suppression du survey " + relation.getIdSurvey());
                    // Supprimer d'abord les enquêteurs liés
                    sessionEnqueteurRepository.deleteByIdSessionSurvey(relation.getId());
                    // Puis supprimer la relation
                    sessionSurveyRepository.delete(relation);
                }
            }

            // 🔥 2. Ajouter les nouvelles relations
            for (Integer surveyId : newSurveyIds) {
                if (!currentSurveyIds.contains(surveyId)) {
                    System.out.println("➕ Ajout du survey " + surveyId);
                    SessionSurvey newRelation = new SessionSurvey(id, surveyId);
                    sessionSurveyRepository.save(newRelation);
                }
            }

            // 🔥 3. Forcer le flush pour que les changements soient persistés
            sessionSurveyRepository.flush();
        }

        // Recharger la session complète
        Session completeSession = sessionRepository.findById(id).orElse(null);
        SessionResponseDTO result = SessionMapper.toDTO(completeSession);

        System.out.println("=== FIN MISE À JOUR ===");
        System.out.println("Nombre de surveys dans la réponse: " +
                (result.getSurveys() != null ? result.getSurveys().size() : 0));

        return result;
    }}