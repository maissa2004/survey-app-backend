package com.example.appenquetes1.mapper;

import com.example.appenquetes1.dto.question.QuestionResponseDTO;
import com.example.appenquetes1.entity.Question;
import com.example.appenquetes1.entity.SectionQuestion;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class QuestionMapper {

    public static QuestionResponseDTO toDTO(SectionQuestion sq) {
        return mapQuestion(sq.getQuestion(), sq, true);
    }

    public static QuestionResponseDTO toDTOFromQuestion(Question q) {
        // 🔥 CHANGEMENT IMPORTANT: includeAnswers = true pour que les questions conditionnelles aient leurs réponses
        return mapQuestion(q, null, true);
    }

    // Méthode unifiée pour mapper une question
    private static QuestionResponseDTO mapQuestion(Question question, SectionQuestion sq, boolean includeAnswers) {
        QuestionResponseDTO dto = new QuestionResponseDTO();

        // IDs
        dto.setId(question.getId());
        if (sq != null) {
            dto.setIdSectionQues(sq.getId());
        }

        // Infos question
        dto.setCode(question.getCode());
        dto.setTitleFr(question.getTitleFr());
        dto.setTitleEn(question.getTitleEn());

        // Type question
        if (question.getNmtypeQuest() != null) {
            System.out.println("Setting type ID: " + question.getNmtypeQuest().getId());
            dto.setIdNmTypeQuest(question.getNmtypeQuest().getId());
            dto.setCodeTypeQues(question.getNmtypeQuest().getCode());
            dto.setLibelleNmtype(question.getNmtypeQuest().getLibelle());
            dto.setLibelleEnNmtype(question.getNmtypeQuest().getLibelleEn());
        } else {
            System.out.println("NmtypeQuest is null for question: " + question.getCode());
            dto.setIdNmTypeQuest(null);
            dto.setCodeTypeQues("");
            dto.setLibelleNmtype("");
            dto.setLibelleEnNmtype("");
        }

        // Answers - inclus pour les questions conditionnelles aussi
        if (includeAnswers && question.getAnswers() != null && !question.getAnswers().isEmpty()) {
            System.out.println("Mapping answers for question " + question.getCode() +
                    ": " + question.getAnswers().size() + " answers");

            dto.setAnswers(
                    question.getAnswers()
                            .stream()
                            .map(AnswerMapper::toDTO)
                            .collect(Collectors.toList())
            );
        } else {
            dto.setAnswers(new ArrayList<>());
        }

        // HasConditions
        dto.setHasConditions(dto.getAnswers() != null &&
                dto.getAnswers().stream()
                        .anyMatch(a -> (a.getCondiQuestion() != null && !a.getCondiQuestion().isEmpty())
                                || (a.getCondiSections() != null && !a.getCondiSections().isEmpty())));

        return dto;
    }
}