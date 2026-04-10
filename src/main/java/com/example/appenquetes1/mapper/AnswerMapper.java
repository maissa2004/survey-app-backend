package com.example.appenquetes1.mapper;

import com.example.appenquetes1.dto.Answer.AnswerResponseDTO;
import com.example.appenquetes1.entity.QuestionAnswers;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class AnswerMapper {
    public static AnswerResponseDTO toDTO(QuestionAnswers ans) {
        if (ans == null) return null;

        System.out.println("Mapping Answer ID: " + ans.getId() +
                ", Code: " + ans.getNmAnswers().getCode() +
                ", hasCondiQuestions: " + (ans.getCondiQuestions() != null ? ans.getCondiQuestions().size() : 0));

        AnswerResponseDTO dto = new AnswerResponseDTO();
        dto.setId(ans.getId());
        if (ans.getNmAnswers() != null) {
            dto.setCode(ans.getNmAnswers().getCode());
            dto.setLibelle(ans.getNmAnswers().getLibelle());
            dto.setLibelleEn(ans.getNmAnswers().getLibelleEn());
            dto.setReference(ans.getNmAnswers().getReference());
        } else {
            dto.setCode(null);
            dto.setLibelle(null);
            dto.setLibelleEn(null);
            dto.setReference(null);
        }

        dto.setDtUpdate(ans.getDtUpdate());

        // Mapper les Questions Conditionnelles
        // Dans AnswerMapper.java, remplacez le mapping des condiQuestions:
        if (ans.getCondiQuestions() != null && !ans.getCondiQuestions().isEmpty()) {
            System.out.println("  Mapping " + ans.getCondiQuestions().size() + " conditional questions");
            dto.setCondiQuestion(
                    ans.getCondiQuestions().stream()
                            .map(q -> QuestionMapper.toDTOFromQuestion(q)) // Cette méthode doit maintenant inclure les answers
                            .collect(Collectors.toList())
            );
        } else {
            dto.setCondiQuestion(new ArrayList<>());
        }

        // Mapper les Sections Conditionnelles
        if (ans.getCondiSections() != null && !ans.getCondiSections().isEmpty()) {
            System.out.println("  Mapping " + ans.getCondiSections().size() + " conditional sections");
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