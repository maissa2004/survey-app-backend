package com.example.appenquetes1.mapper;

import com.example.appenquetes1.dto.session.SessionRequestDTO;
import com.example.appenquetes1.dto.session.SessionResponseDTO;
import com.example.appenquetes1.entity.Session;

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
        dto.setIdSurvey(session.getIdSurvey());

        if (session.getSurvey() != null) {
            dto.setSurveyCode(session.getSurvey().getCode());
            dto.setSurveyLibelle(session.getSurvey().getLibelle());
        }

        return dto;
    }

    public static Session toEntity(SessionRequestDTO dto) {
        if (dto == null) return null;

        Session session = new Session();
        session.setIntitule(dto.getIntitule());
        session.setDateDebut(dto.getDateDebut());
        session.setDateFin(dto.getDateFin());
        session.setIdSurvey(dto.getIdSurvey());

        if (dto.getStatus() != null) {
            session.setStatus(dto.getStatus());
        }

        return session;
    }
}