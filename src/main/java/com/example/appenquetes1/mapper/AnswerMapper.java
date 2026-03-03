package com.example.appenquetes1.mapper;

import com.example.appenquetes1.dto.Answer.AnswerResponseDTO;
import com.example.appenquetes1.entity.QuestionAnswers;

import java.util.List;
import java.util.stream.Collectors;

public class AnswerMapper {
    public static AnswerResponseDTO toDTO(QuestionAnswers ans) {

        AnswerResponseDTO dto = new AnswerResponseDTO();

        dto.setId(ans.getId());
        dto.setCode(ans.getCode());
        dto.setLibelle(ans.getLibelle());
        dto.setLibelleEn(ans.getLibelleEn());


        return dto;
    }

}
