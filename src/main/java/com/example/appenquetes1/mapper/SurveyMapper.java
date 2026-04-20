package com.example.appenquetes1.mapper;

import com.example.appenquetes1.dto.etatSurvey.EtatSurveyResponseDTO;
import com.example.appenquetes1.dto.survey.SurveyResponseDTO;
import com.example.appenquetes1.entity.EtatSurvey;
import com.example.appenquetes1.entity.Survey;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class SurveyMapper {

    public static SurveyResponseDTO toDTO(Survey survey) {

        SurveyResponseDTO dto = new SurveyResponseDTO();

        dto.setId(survey.getId());
        dto.setCode(survey.getCode());
        dto.setLibelle(survey.getLibelle());
        dto.setLibelleEn(survey.getLibelleEn());
        dto.setDtAdd(survey.getDtAdd());
        dto.setDtUpdate(survey.getDtUpdate());
        dto.setFormReference(survey.isFormReference());

        dto.setSections(
                survey.getSections()
                        .stream()
                        // On garde ce filtre pour les sections conditionnelles
                        .filter(section -> section.getParentAnswer() == null)
                        .map(SectionMapper::toDTO)
                        .collect(Collectors.toList())
        );

        if (survey.getEtatSurvey() != null) {
            dto.setIdEtatSurvey(survey.getEtatSurvey().getid());
        } else {
            dto.setIdEtatSurvey(null);
        }

        // Gestion des sections
        if (survey.getSections() != null && !survey.getSections().isEmpty()) {
            dto.setSections(
                    survey.getSections()
                            .stream()
                            .filter(section -> section.getParentAnswer() == null)
                            .map(SectionMapper::toDTO)
                            .collect(Collectors.toList())
            );
        } else {
            dto.setSections(Collections.emptyList());
        }

        return dto;
    }

    public static List<SurveyResponseDTO> toDTOList(List<Survey> surveys) {
        if (surveys == null) {
            return Collections.emptyList();
        }
        return surveys.stream()
                .map(SurveyMapper::toDTO)
                .collect(Collectors.toList());
    }
}
