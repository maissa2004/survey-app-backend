package com.example.appenquetes1.dto.useranswer;
import java.util.List;
public class UserAnswersSurvey {
    private Integer idSurvey;
    private Integer idUser;
    private List<UserAnswerRequestDTO> responses;

    // getters et setters
    public Integer getIdSurvey() {
        return idSurvey;
    }

    public void setIdSurvey(Integer idSurvey) {
        this.idSurvey = idSurvey;
    }

    public Integer getIdUser() {
        return idUser;
    }

    public void setIdUser(Integer idUser) {
        this.idUser = idUser;
    }

    public List<UserAnswerRequestDTO> getResponses() {
        return responses;
    }

    public void setResponses(List<UserAnswerRequestDTO> responses) {
        this.responses = responses;
    }
}