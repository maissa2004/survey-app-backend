// com.example.appenquetes1.dto.session.SessionRequestDTO.java
package com.example.appenquetes1.dto.session;

import com.example.appenquetes1.entity.Session;
import java.time.LocalDateTime;
import java.util.List;

public class SessionRequestDTO {
    private String intitule;
    private LocalDateTime dateDebut;
    private LocalDateTime dateFin;
    private Session.Status status;
    private List<Integer> idSurveys;  // Liste des IDs des surveys

    // Getters et Setters
    public String getIntitule() { return intitule; }
    public void setIntitule(String intitule) { this.intitule = intitule; }

    public LocalDateTime getDateDebut() { return dateDebut; }
    public void setDateDebut(LocalDateTime dateDebut) { this.dateDebut = dateDebut; }

    public LocalDateTime getDateFin() { return dateFin; }
    public void setDateFin(LocalDateTime dateFin) { this.dateFin = dateFin; }

    public Session.Status getStatus() { return status; }
    public void setStatus(Session.Status status) { this.status = status; }

    public List<Integer> getIdSurveys() { return idSurveys; }
    public void setIdSurveys(List<Integer> idSurveys) { this.idSurveys = idSurveys; }
}