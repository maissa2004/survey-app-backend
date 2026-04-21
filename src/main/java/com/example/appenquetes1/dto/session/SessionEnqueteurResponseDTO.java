package com.example.appenquetes1.dto.session;

import java.time.LocalDateTime;

public class SessionEnqueteurResponseDTO {
    private Integer id;
    private Integer idSessionSurvey;
    private Integer idUser;
    private String username;
    private String email;
    private String phone;
    private Integer idSession;
    private Integer idSurvey;
    private String surveyCode;
    private String surveyLibelle;
    private LocalDateTime dateAffectation;

    // Getters et Setters
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public Integer getIdSessionSurvey() { return idSessionSurvey; }
    public void setIdSessionSurvey(Integer idSessionSurvey) { this.idSessionSurvey = idSessionSurvey; }

    public Integer getIdUser() { return idUser; }
    public void setIdUser(Integer idUser) { this.idUser = idUser; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }

    public Integer getIdSession() { return idSession; }
    public void setIdSession(Integer idSession) { this.idSession = idSession; }

    public Integer getIdSurvey() { return idSurvey; }
    public void setIdSurvey(Integer idSurvey) { this.idSurvey = idSurvey; }

    public String getSurveyCode() { return surveyCode; }
    public void setSurveyCode(String surveyCode) { this.surveyCode = surveyCode; }

    public String getSurveyLibelle() { return surveyLibelle; }
    public void setSurveyLibelle(String surveyLibelle) { this.surveyLibelle = surveyLibelle; }

    public LocalDateTime getDateAffectation() { return dateAffectation; }
    public void setDateAffectation(LocalDateTime dateAffectation) { this.dateAffectation = dateAffectation; }
}