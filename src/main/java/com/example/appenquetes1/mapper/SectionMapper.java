package com.example.appenquetes1.mapper;

import com.example.appenquetes1.dto.question.QuestionResponseDTO;
import com.example.appenquetes1.dto.section.SectionResponseDTO;
import com.example.appenquetes1.entity.Section;

import java.util.List;
import java.util.stream.Collectors;

public class SectionMapper {

    public static SectionResponseDTO toDTO(Section section) {

        SectionResponseDTO dto = new SectionResponseDTO();

        dto.setId(section.getId());
        dto.setCode(section.getCode());
        dto.setTitle(section.getTitle());
        dto.setTitleEn(section.getTitleEn());
        dto.setConditionnel(section.isConditionnel());
        dto.setOrdre(section.getOrdre());

        List<QuestionResponseDTO> questions =
                section.getSectionQuestions()
                        .stream()
                        .map(QuestionMapper::toDTO) // ✅ direct sq
                        .toList();

        dto.setQuestions(questions);

        return dto;
    }
}


