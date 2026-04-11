package com.example.appenquetes1.dto.session;

import com.example.appenquetes1.entity.Session;
import java.time.LocalDateTime;

public class SessionRequestDTO {
    private String intitule;
    private LocalDateTime dateDebut;
    private LocalDateTime dateFin;
    private Session.Status status;
    private Integer idSurvey;

    // Getters et Setters
    public String getIntitule() { return intitule; }
    public void setIntitule(String intitule) { this.intitule = intitule; }

    public LocalDateTime getDateDebut() { return dateDebut; }
    public void setDateDebut(LocalDateTime dateDebut) { this.dateDebut = dateDebut; }

    public LocalDateTime getDateFin() { return dateFin; }
    public void setDateFin(LocalDateTime dateFin) { this.dateFin = dateFin; }

    public Session.Status getStatus() { return status; }
    public void setStatus(Session.Status status) { this.status = status; }

    public Integer getIdSurvey() { return idSurvey; }
    public void setIdSurvey(Integer idSurvey) { this.idSurvey = idSurvey; }
}