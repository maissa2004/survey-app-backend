package com.example.appenquetes1.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "etat_survey")
public class EtatSurvey {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private boolean etat;
    private LocalDateTime dtUpdate;

    public EtatSurvey() {
    }

    public EtatSurvey(int id, boolean etat, LocalDateTime dtUpdate) {
        this.id = id;
        this.etat = etat;
        this.dtUpdate = dtUpdate;
    }



    public Integer getid() {
        return id;
    }

    public void setid(Integer id) {
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
