package com.example.appenquetes1.mapper;

import com.example.appenquetes1.dto.question.QuestionResponseDTO;
import com.example.appenquetes1.entity.Question;
import com.example.appenquetes1.entity.SectionQuestion;

public class SectionQuestionMapper {
    public static QuestionResponseDTO toDTO(SectionQuestion sq) {

        Question q = sq.getQuestion();

        QuestionResponseDTO dto = new QuestionResponseDTO();

        dto.setId(q.getId());
        dto.setIdSectionQues(sq.getId()); // 🔥 IMPORTANT
        dto.setTitleFr(q.getTitleFr());
        dto.setTitleEn(q.getTitleEn());
        dto.setRequired(sq.isRequired());
        dto.setConditionnel(q.getConditionnel());
        dto.setCode(q.getCode());

        dto.setAnswers(
                q.getAnswers()
                        .stream()
                        .map(AnswerMapper::toDTO)
                        .toList()
        );

        return dto;
    }

}
