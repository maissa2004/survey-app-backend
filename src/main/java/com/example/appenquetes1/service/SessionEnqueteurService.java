// com.example.appenquetes1.service.SessionEnqueteurService.java
package com.example.appenquetes1.service;

import com.example.appenquetes1.dto.session.SessionEnqueteurRequestDTO;
import com.example.appenquetes1.dto.session.SessionEnqueteurResponseDTO;
import com.example.appenquetes1.entity.SessionEnqueteur;
import com.example.appenquetes1.entity.SessionSurvey;
import com.example.appenquetes1.entity.User;
import com.example.appenquetes1.mapper.SessionEnqueteurMapper;
import com.example.appenquetes1.repository.SessionEnqueteurRepository;
import com.example.appenquetes1.repository.SessionSurveyRepository;
import com.example.appenquetes1.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SessionEnqueteurService {

    @Autowired
    private SessionEnqueteurRepository sessionEnqueteurRepository;

    @Autowired
    private SessionSurveyRepository sessionSurveyRepository;

    @Autowired
    private UserRepository userRepository;

    public List<SessionEnqueteurResponseDTO> getEnqueteursBySessionSurvey(Integer sessionSurveyId) {
        return sessionEnqueteurRepository.findByIdSessionSurvey(sessionSurveyId).stream()
                .map(SessionEnqueteurMapper::toDTO)
                .collect(Collectors.toList());
    }

    public List<SessionEnqueteurResponseDTO> getEnqueteursBySession(Integer sessionId) {
        List<Integer> sessionSurveyIds = sessionSurveyRepository.findByIdSession(sessionId).stream()
                .map(SessionSurvey::getId)
                .collect(Collectors.toList());

        return sessionEnqueteurRepository.findByIdSessionSurveyIn(sessionSurveyIds).stream()
                .map(SessionEnqueteurMapper::toDTO)
                .collect(Collectors.toList());
    }

    public List<User> getAvailableEnqueteursForSessionSurvey(Integer sessionSurveyId) {
        List<Integer> assignedUserIds = sessionEnqueteurRepository.findByIdSessionSurvey(sessionSurveyId).stream()
                .map(SessionEnqueteur::getIdUser)
                .collect(Collectors.toList());

        if (assignedUserIds.isEmpty()) {
            return userRepository.findByRole(User.Role.enqueteur);
        } else {
            return userRepository.findByRoleAndIdNotIn(User.Role.enqueteur, assignedUserIds);
        }
    }

    @Transactional
    public SessionEnqueteurResponseDTO assignEnqueteur(SessionEnqueteurRequestDTO request) {
        // Vérifier si le session_survey existe
        SessionSurvey sessionSurvey = sessionSurveyRepository.findById(request.getIdSessionSurvey()).orElse(null);
        if (sessionSurvey == null) {
            throw new RuntimeException("SessionSurvey non trouvé");
        }

        // Vérifier si l'utilisateur existe et est un enquêteur
        User user = userRepository.findById(request.getIdUser()).orElse(null);
        if (user == null) {
            throw new RuntimeException("Utilisateur non trouvé");
        }
        if (user.getRole() != User.Role.enqueteur) {
            throw new RuntimeException("L'utilisateur doit avoir le rôle enquêteur");
        }

        // Vérifier si déjà affecté
        if (sessionEnqueteurRepository.existsByIdSessionSurveyAndIdUser(request.getIdSessionSurvey(), request.getIdUser())) {
            throw new RuntimeException("Cet enquêteur est déjà affecté à ce survey");
        }

        SessionEnqueteur entity = SessionEnqueteurMapper.toEntity(request);
        SessionEnqueteur saved = sessionEnqueteurRepository.save(entity);

        // Charger les relations pour le DTO
        saved.setSessionSurvey(sessionSurvey);
        saved.setUser(user);

        return SessionEnqueteurMapper.toDTO(saved);
    }

    @Transactional
    public void removeEnqueteur(Integer sessionSurveyId, Integer userId) {
        if (!sessionEnqueteurRepository.existsByIdSessionSurveyAndIdUser(sessionSurveyId, userId)) {
            throw new RuntimeException("Affectation non trouvée");
        }
        sessionEnqueteurRepository.deleteByIdSessionSurveyAndIdUser(sessionSurveyId, userId);
    }
}