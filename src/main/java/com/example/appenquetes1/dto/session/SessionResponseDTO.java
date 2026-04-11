package com.example.appenquetes1.dto.session;

import java.time.LocalDateTime;

public class SessionResponseDTO {
    private Integer id;
    private String intitule;
    private LocalDateTime dateDebut;
    private LocalDateTime dateFin;
    private String status;
    private LocalDateTime dtCreate;
    private LocalDateTime dtUpdate;
    private Integer idSurvey;
    private String surveyCode;
    private String surveyLibelle;

    // Getters et Setters
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public String getIntitule() { return intitule; }
    public void setIntitule(String intitule) { this.intitule = intitule; }

    public LocalDateTime getDateDebut() { return dateDebut; }
    public void setDateDebut(LocalDateTime dateDebut) { this.dateDebut = dateDebut; }

    public LocalDateTime getDateFin() { return dateFin; }
    public void setDateFin(LocalDateTime dateFin) { this.dateFin = dateFin; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public LocalDateTime getDtCreate() { return dtCreate; }
    public void setDtCreate(LocalDateTime dtCreate) { this.dtCreate = dtCreate; }

    public LocalDateTime getDtUpdate() { return dtUpdate; }
    public void setDtUpdate(LocalDateTime dtUpdate) { this.dtUpdate = dtUpdate; }

    public Integer getIdSurvey() { return idSurvey; }
    public void setIdSurvey(Integer idSurvey) { this.idSurvey = idSurvey; }

    public String getSurveyCode() { return surveyCode; }
    public void setSurveyCode(String surveyCode) { this.surveyCode = surveyCode; }

    public String getSurveyLibelle() { return surveyLibelle; }
    public void setSurveyLibelle(String surveyLibelle) { this.surveyLibelle = surveyLibelle; }
}