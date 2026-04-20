// com.example.appenquetes1.mapper.SessionEnqueteurMapper.java
package com.example.appenquetes1.mapper;

import com.example.appenquetes1.dto.session.SessionEnqueteurRequestDTO;
import com.example.appenquetes1.dto.session.SessionEnqueteurResponseDTO;
import com.example.appenquetes1.entity.SessionEnqueteur;

public class SessionEnqueteurMapper {

    public static SessionEnqueteurResponseDTO toDTO(SessionEnqueteur entity) {
        if (entity == null) return null;

        SessionEnqueteurResponseDTO dto = new SessionEnqueteurResponseDTO();
        dto.setId(entity.getId());
        dto.setIdSessionSurvey(entity.getIdSessionSurvey());
        dto.setIdUser(entity.getIdUser());
        dto.setDateAffectation(entity.getDateAffectation());

        if (entity.getUser() != null) {
            dto.setUsername(entity.getUser().getUsername());
            dto.setEmail(entity.getUser().getEmail());
            dto.setPhone(entity.getUser().getPhone());
        }

        if (entity.getSessionSurvey() != null) {
            dto.setIdSession(entity.getSessionSurvey().getIdSession());
            dto.setIdSurvey(entity.getSessionSurvey().getIdSurvey());
            if (entity.getSessionSurvey().getSurvey() != null) {
                dto.setSurveyCode(entity.getSessionSurvey().getSurvey().getCode());
                dto.setSurveyLibelle(entity.getSessionSurvey().getSurvey().getLibelle());
            }
        }

        return dto;
    }

    public static SessionEnqueteur toEntity(SessionEnqueteurRequestDTO dto) {
        if (dto == null) return null;
        return new SessionEnqueteur(dto.getIdSessionSurvey(), dto.getIdUser());
    }
}