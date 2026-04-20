package com.example.appenquetes1.mapper;

import com.example.appenquetes1.dto.survey.SurveyResponseDTO;
import com.example.appenquetes1.entity.Survey;

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
        dto.setValid(survey.isValid());
        dto.setFormReference(survey.isFormReference());
        dto.setSections(
                survey.getSections()
                        .stream()
                        // On garde ce filtre pour les sections conditionnelles
                        .filter(section -> section.getParentAnswer() == null)
                        .map(SectionMapper::toDTO)
                        .collect(Collectors.toList())
        );

        return dto;
    }
}
