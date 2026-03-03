package com.example.appenquetes1.mapper;

import com.example.appenquetes1.dto.question.QuestionResponseDTO;
import com.example.appenquetes1.entity.Question;
import com.example.appenquetes1.entity.SectionQuestion;

import java.util.List;
import java.util.stream.Collectors;

public class QuestionMapper {

    public static QuestionResponseDTO toDTO(SectionQuestion sq) {

        QuestionResponseDTO dto = new QuestionResponseDTO();

        // 🔥 IDs
        dto.setId(sq.getQuestion().getId());
        dto.setIdSectionQues(sq.getId());

        // 🔥 Infos question
        dto.setCode(sq.getQuestion().getCode());
        dto.setTitleFr(sq.getQuestion().getTitleFr());
        dto.setTitleEn(sq.getQuestion().getTitleEn());

        // 🔥 Flags
        dto.setRequired(sq.isRequired());
        dto.setConditionnel(sq.isConditionnel());

        // 🔥 Type question (IMPORTANT)
        if (sq.getQuestion().getNmtypeQuest() != null) {
            dto.setCodeTypeQues(
                    sq.getQuestion().getNmtypeQuest().getCode()
            );

            dto.setLibelleNmtype(
                    sq.getQuestion().getNmtypeQuest().getLibelle()
            );

            dto.setLibelleEnNmtype(
                    sq.getQuestion().getNmtypeQuest().getLibelleEn()
            );
        }

        // 🔥 Answers
        if (sq.getQuestionAnswers() != null) {
            dto.setAnswers(
                    sq.getQuestionAnswers()
                            .stream()
                            .map(AnswerMapper::toDTO)
                            .toList()
            );
        } else {
            dto.setAnswers(java.util.List.of());
        }

        // 🔥 Conditions
        dto.setHasConditions(!dto.getAnswers().isEmpty());

        return dto;
    }

    public static QuestionResponseDTO toDTOFromQuestion(Question q) {

        QuestionResponseDTO dto = new QuestionResponseDTO();

        dto.setId(q.getId());
        dto.setCode(q.getCode());
        dto.setTitleFr(q.getTitleFr());
        dto.setTitleEn(q.getTitleEn());

        // ⚠️ On ne met PAS les answers pour éviter la boucle infinie
        dto.setAnswers(List.of());
        dto.setHasConditions(false);

        return dto;
    }

}

