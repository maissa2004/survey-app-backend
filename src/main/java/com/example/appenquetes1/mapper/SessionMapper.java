package com.example.appenquetes1.mapper;

import com.example.appenquetes1.dto.session.SessionRequestDTO;
import com.example.appenquetes1.dto.session.SessionResponseDTO;
import com.example.appenquetes1.entity.Session;
import com.example.appenquetes1.entity.SessionSurvey;
import com.example.appenquetes1.entity.Survey;

import java.util.stream.Collectors;

public class SessionMapper {

    public static SessionResponseDTO toDTO(Session session) {
        if (session == null) return null;

        SessionResponseDTO dto = new SessionResponseDTO();
        dto.setId(session.getId());
        dto.setIntitule(session.getIntitule());
        dto.setDateDebut(session.getDateDebut());
        dto.setDateFin(session.getDateFin());
        dto.setStatus(session.getStatus().name());
        dto.setDtCreate(session.getDtCreate());
        dto.setDtUpdate(session.getDtUpdate());

        // Récupérer la liste des surveys
        if (session.getSessionSurveys() != null && !session.getSessionSurveys().isEmpty()) {
            // Premier survey pour l'affichage rapide
            SessionSurvey first = session.getSessionSurveys().get(0);
            dto.setIdSurvey(first.getIdSurvey());
            if (first.getSurvey() != null) {
                dto.setSurveyCode(first.getSurvey().getCode());
                dto.setSurveyLibelle(first.getSurvey().getLibelle());
            }

            // 🔥 LISTE COMPLÈTE DES SURVEYS AVEC sessionSurveyId
            dto.setSurveys(session.getSessionSurveys().stream()
                    .filter(ss -> ss.getSurvey() != null)
                    .map(ss -> new SessionResponseDTO.SurveyInfo(
                            ss.getSurvey().getId(),
                            ss.getId(),  // 🔥 sessionSurveyId (l'ID de la table de liaison)
                            ss.getSurvey().getCode(),
                            ss.getSurvey().getLibelle()
                    ))
                    .collect(Collectors.toList()));
        }

        return dto;
    }

    public static Session toEntity(SessionRequestDTO dto) {
        if (dto == null) return null;

        Session session = new Session();
        session.setIntitule(dto.getIntitule());
        session.setDateDebut(dto.getDateDebut());
        session.setDateFin(dto.getDateFin());

        if (dto.getStatus() != null) {
            session.setStatus(dto.getStatus());
        }

        return session;
    }
}