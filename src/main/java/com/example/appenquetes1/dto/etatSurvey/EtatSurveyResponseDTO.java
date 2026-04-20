package com.example.appenquetes1.dto.etatSurvey;

import java.util.Date;

public class EtatSurveyResponseDTO {
    private Integer id;
    private boolean etat;

    public EtatSurveyResponseDTO() {
    }

    public EtatSurveyResponseDTO(Integer id, boolean etat) {
        this.id = id;
        this.etat = etat;
    }

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public boolean isEtat() { return etat; }
    public void setEtat(boolean etat) { this.etat = etat; }
}
