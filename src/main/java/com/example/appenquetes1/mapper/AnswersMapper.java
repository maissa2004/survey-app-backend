// com.example.appenquetes1.mapper.AnswersMapper.java
package com.example.appenquetes1.mapper;

import com.example.appenquetes1.dto.useranswer.UserAnswerRequestDTO;
import com.example.appenquetes1.dto.useranswer.UserAnswerResponseDTO;
import com.example.appenquetes1.entity.Answers;
import com.example.appenquetes1.entity.NmAnswers;

import java.util.ArrayList;

public class AnswersMapper {

    public static UserAnswerResponseDTO toDTO(Answers answer) {
        if (answer == null) return null;

        UserAnswerResponseDTO dto = new UserAnswerResponseDTO();
        dto.setId(answer.getId());
        dto.setIdSectionQuest(answer.getIdSectionQuest());
        dto.setIdUser(answer.getIdUser());
        dto.setValue(answer.getValue());
        dto.setFileName(answer.getFileName());
        dto.setReferenceCode(answer.getReferenceCode());
        dto.setFileType(answer.getFileType());
        dto.setFileSize(answer.getFileSize());

        dto.setIdNmAnswer(
                answer.getNmAnswers() != null
                        ? answer.getNmAnswers().stream()
                        .map(NmAnswers::getId)
                        .toList()
                        : null
        );
        dto.setDtUpdate(answer.getDtUpdate());
        dto.setCodeQuestion(answer.getCodeQuestion());
        dto.setIdSurvey(answer.getIdSurvey());

        if (answer.getUser() != null) {
            dto.setUsername(answer.getUser().getUsername());
        }
        if (answer.getNmAnswers() != null) {
            dto.setNmAnswerCode(
                    answer.getNmAnswers().stream()
                            .map(NmAnswers::getCode)
                            .toList()
                            .toString()
            );

            dto.setNmAnswerLibelle(
                    answer.getNmAnswers().stream()
                            .map(NmAnswers::getLibelle)
                            .toList()
                            .toString()
            );
        }
        dto.setSubmissionId(answer.getSubmissionId());


        return dto;
    }

    public static Answers toEntity(UserAnswerRequestDTO dto) {
        if (dto == null) return null;

        Answers answer = new Answers();
        answer.setIdSectionQuest(dto.getIdSectionQuest());
        answer.setIdUser(dto.getIdUser());
        answer.setValue(dto.getValue());
        answer.setFileName(dto.getFileName() != null ? dto.getFileName() : "");
        answer.setReferenceCode(dto.getReferenceCode() != null ? dto.getReferenceCode() : "");
        answer.setFileType(dto.getFileType() != null ? dto.getFileType() : "");
        answer.setFileSize(dto.getFileSize() != null ? dto.getFileSize() : 0);
        if (dto.getIdNmAnswer() != null && !dto.getIdNmAnswer().isEmpty()) {
            answer.setNmAnswers(
                dto.getIdNmAnswer().stream()
                        .map(id -> {
                            NmAnswers n = new NmAnswers();
                            n.setId(id);
                            return n;
                        }).toList()
        );}
        answer.setCodeQuestion(dto.getCodeQuestion());
        answer.setIdSurvey(dto.getIdSurvey());
        answer.setSubmissionId(dto.getSubmissionId());
        return answer;
    }
}