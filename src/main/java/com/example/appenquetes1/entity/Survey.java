package com.example.appenquetes1.entity;


import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "survey")
public class Survey {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String code;
    private String libelle;
    private String libelleEn;
    @CreationTimestamp
    private LocalDate dtAdd;
    @UpdateTimestamp
    private LocalDate dtUpdate;
    private boolean isValid;
    private boolean isFormReference;

    @OneToMany(mappedBy = "survey", fetch = FetchType.LAZY)
    private Set<Section> sections = new HashSet<>();

    @ManyToOne
    @JoinColumn(name = "id_etat_survey")
    private EtatSurvey etatSurvey;




    public EtatSurvey getEtatSurvey() {
        return etatSurvey;
    }

    public void setEtatSurvey(EtatSurvey etatSurvey) {
        this.etatSurvey = etatSurvey;
    }
    @Transient
    private Integer idEtatSurvey;


    public Survey() {}

    public Survey(Integer id, String code, String libelle, String libelleEn,
                  LocalDate dtAdd, LocalDate dtUpdate,
                  boolean isValid, boolean isFormReference,
                  Set<Section> sections) {

        this.id = id;
        this.code = code;
        this.libelle = libelle;
        this.libelleEn = libelleEn;
        this.dtAdd = dtAdd;
        this.dtUpdate = dtUpdate;
        this.isValid = isValid;
        this.isFormReference = isFormReference;
        this.sections = sections;

    }


    public Set<Section> getSections() {
        return sections;
    }

    public void setSections(Set<Section> sections) {
        this.sections = sections;
    }

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public String getCode() { return code; }
    public void setCode(String code) { this.code = code; }

    public String getLibelle() { return libelle; }
    public void setLibelle(String libelle) { this.libelle = libelle; }

    public String getLibelleEn() { return libelleEn; }
    public void setLibelleEn(String libelleEn) { this.libelleEn = libelleEn; }

    public LocalDate getDtAdd() { return dtAdd; }
    public void setDtAdd(LocalDate dtAdd) { this.dtAdd = dtAdd; }

    public LocalDate getDtUpdate() { return dtUpdate; }
    public void setDtUpdate(LocalDate dtUpdate) { this.dtUpdate = dtUpdate; }

    public boolean isValid() { return isValid; }
    public void setValid(boolean valid) { isValid = valid; }

    public boolean isFormReference() { return isFormReference; }
    public void setFormReference(boolean formReference) {
        isFormReference = formReference;
    }

    public Integer getIdEtatSurvey() {
        return etatSurvey != null ? etatSurvey.getid() : null;
    }

    public void setIdEtatSurvey(Integer idEtatSurvey) {
        this.idEtatSurvey = idEtatSurvey;
        if (idEtatSurvey != null) {
            EtatSurvey etat = new EtatSurvey();
            etat.setid(idEtatSurvey);
            this.etatSurvey = etat;
        }
    }

}

