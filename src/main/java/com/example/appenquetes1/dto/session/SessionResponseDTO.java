// com.example.appenquetes1.dto.session.SessionResponseDTO.java
package com.example.appenquetes1.dto.session;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class SessionResponseDTO {
    private Integer id;
    private String intitule;
    private LocalDateTime dateDebut;
    private LocalDateTime dateFin;
    private String status;
    private LocalDateTime dtCreate;
    private LocalDateTime dtUpdate;

    // Pour l'affichage (premier survey)
    private Integer idSurvey;
    private String surveyCode;
    private String surveyLibelle;

    // Liste complète des surveys
    private List<SurveyInfo> surveys;

    // Liste des enquêteurs
    private List<SessionEnqueteurResponseDTO> enqueteurs;

    // ========== Classe interne SurveyInfo ==========
    public static class SurveyInfo {
        private Integer id;
        private Integer sessionSurveyId;  // ← NOUVEAU
        private String code;
        private String libelle;
        private List<SessionEnqueteurResponseDTO> enqueteurs;

        // Constructeur avec 4 paramètres (ID survey, ID session_survey, code, libelle)
        public SurveyInfo(Integer id, Integer sessionSurveyId, String code, String libelle) {
            this.id = id;
            this.sessionSurveyId = sessionSurveyId;
            this.code = code;
            this.libelle = libelle;
            this.enqueteurs = new ArrayList<>();
        }

        // Constructeur avec 3 paramètres (pour compatibilité)
        public SurveyInfo(Integer id, String code, String libelle) {
            this.id = id;
            this.code = code;
            this.libelle = libelle;
            this.enqueteurs = new ArrayList<>();
        }

        // Getters
        public Integer getId() { return id; }
        public Integer getSessionSurveyId() { return sessionSurveyId; }
        public String getCode() { return code; }
        public String getLibelle() { return libelle; }
        public List<SessionEnqueteurResponseDTO> getEnqueteurs() { return enqueteurs; }

        // Setters
        public void setId(Integer id) { this.id = id; }
        public void setSessionSurveyId(Integer sessionSurveyId) { this.sessionSurveyId = sessionSurveyId; }
        public void setCode(String code) { this.code = code; }
        public void setLibelle(String libelle) { this.libelle = libelle; }
        public void setEnqueteurs(List<SessionEnqueteurResponseDTO> enqueteurs) { this.enqueteurs = enqueteurs; }
    }

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

    public List<SurveyInfo> getSurveys() { return surveys; }
    public void setSurveys(List<SurveyInfo> surveys) { this.surveys = surveys; }

    public List<SessionEnqueteurResponseDTO> getEnqueteurs() { return enqueteurs; }
    public void setEnqueteurs(List<SessionEnqueteurResponseDTO> enqueteurs) { this.enqueteurs = enqueteurs; }
}