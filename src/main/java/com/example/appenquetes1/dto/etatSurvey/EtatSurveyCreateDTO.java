package com.example.appenquetes1.dto.etatSurvey;

import java.time.LocalDateTime;

public class EtatSurveyCreateDTO {
    private Integer id;
    private boolean etat;
    private LocalDateTime dtUpdate;

    public EtatSurveyCreateDTO() {
    }

    public EtatSurveyCreateDTO(Integer id, boolean etat, LocalDateTime dtUpdate) {
        this.id = id;
        this.etat = etat;
        this.dtUpdate = dtUpdate;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public boolean isEtat() {
        return etat;
    }

    public void setEtat(boolean etat) {
        this.etat = etat;
    }

    public LocalDateTime getDtUpdate() {
        return dtUpdate;
    }

    public void setDtUpdate(LocalDateTime dtUpdate) {
        this.dtUpdate = dtUpdate;
    }
}
