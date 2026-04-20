package com.example.appenquetes1.dto.session;

public class SessionEnqueteurRequestDTO {
    private Integer idSessionSurvey;
    private Integer idUser;

    public SessionEnqueteurRequestDTO() {}

    public SessionEnqueteurRequestDTO(Integer idSessionSurvey, Integer idUser) {
        this.idSessionSurvey = idSessionSurvey;
        this.idUser = idUser;
    }

    public Integer getIdSessionSurvey() { return idSessionSurvey; }
    public void setIdSessionSurvey(Integer idSessionSurvey) { this.idSessionSurvey = idSessionSurvey; }

    public Integer getIdUser() { return idUser; }
    public void setIdUser(Integer idUser) { this.idUser = idUser; }
}