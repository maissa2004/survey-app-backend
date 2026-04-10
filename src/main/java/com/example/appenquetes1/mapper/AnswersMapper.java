// com.example.appenquetes1.mapper.AnswersMapper.java
package com.example.appenquetes1.mapper;

import com.example.appenquetes1.dto.useranswer.UserAnswerRequestDTO;
import com.example.appenquetes1.dto.useranswer.UserAnswerResponseDTO;
import com.example.appenquetes1.entity.Answers;

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
        dto.setIdNmAnswer(answer.getIdNmAnswer());
        dto.setDtUpdate(answer.getDtUpdate());
        dto.setCodeQuestion(answer.getCodeQuestion());
        dto.setIdSurvey(answer.getIdSurvey());

        if (answer.getUser() != null) {
            dto.setUsername(answer.getUser().getUsername());
        }
        if (answer.getNmAnswer() != null) {
            dto.setNmAnswerLibelle(answer.getNmAnswer().getLibelle());
            dto.setNmAnswerCode(answer.getNmAnswer().getCode());
        }
        if (answer.getSurvey() != null) {
            dto.setSurveyCode(answer.getSurvey().getCode());
            dto.setSurveyLibelle(answer.getSurvey().getLibelle());
        }

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
        answer.setIdNmAnswer(dto.getIdNmAnswer());
        answer.setCodeQuestion(dto.getCodeQuestion());
        answer.setIdSurvey(dto.getIdSurvey());

        return answer;
    }
}