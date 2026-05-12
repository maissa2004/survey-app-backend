package com.example.appenquetes1.mapper;

import com.example.appenquetes1.dto.Answer.AnswerResponseDTO;
import com.example.appenquetes1.entity.QuestionAnswers;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class AnswerMapper {
    public static AnswerResponseDTO toDTO(QuestionAnswers ans) {
        if (ans == null) return null;

        AnswerResponseDTO dto = new AnswerResponseDTO();
        dto.setId(ans.getId());
        dto.setDtUpdate(ans.getDtUpdate());

        // Sécurité pour nmAnswers null
        if (ans.getNmAnswers() != null) {
            dto.setCode(ans.getNmAnswers().getCode());
            dto.setLibelle(ans.getNmAnswers().getLibelle());
            dto.setLibelleEn(ans.getNmAnswers().getLibelleEn());
            dto.setReference(ans.getNmAnswers().getReference());
        }

        // Questions conditionnelles
        if (ans.getCondiQuestions() != null && !ans.getCondiQuestions().isEmpty()) {
            dto.setCondiQuestion(
                    ans.getCondiQuestions().stream()
                            .map(QuestionMapper::toDTOFromQuestion)
                            .collect(Collectors.toList())
            );
        } else {
            dto.setCondiQuestion(new ArrayList<>());
        }

        // Sections conditionnelles
        if (ans.getCondiSections() != null && !ans.getCondiSections().isEmpty()) {
            dto.setCondiSections(
                    ans.getCondiSections().stream()
                            .map(SectionMapper::toDTO)
                            .collect(Collectors.toList())
            );
        } else {
            dto.setCondiSections(new ArrayList<>());
        }

        return dto;
    }
}