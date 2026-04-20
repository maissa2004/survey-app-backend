package com.example.appenquetes1.dto.survey;

public class SurveyCreateDTO {
    private String code;
    private String libelle;
    private String libelleEn;
    private boolean isFormReference;
    private Integer idEtatSurvey;  // ← L'ID de l'état

    // Getters et Setters
    public String getCode() { return code; }
    public void setCode(String code) { this.code = code; }

    public String getLibelle() { return libelle; }
    public void setLibelle(String libelle) { this.libelle = libelle; }

    public String getLibelleEn() { return libelleEn; }
    public void setLibelleEn(String libelleEn) { this.libelleEn = libelleEn; }

    public boolean isFormReference() { return isFormReference; }
    public void setFormReference(boolean formReference) { isFormReference = formReference; }

    public Integer getIdEtatSurvey() { return idEtatSurvey; }
    public void setIdEtatSurvey(Integer idEtatSurvey) { this.idEtatSurvey = idEtatSurvey; }
}