package com.example.appenquetes1.dto.nmTypeQuest;

import java.time.LocalDate;

public class NmTypeQuestCreateDTO {
    private Integer id;
    private String code;
    private String libelle;
    private String libelleEn;
    private int coefficient;
    private LocalDate dtUpdate;

    public NmTypeQuestCreateDTO() {
    }

    public NmTypeQuestCreateDTO(Integer id, String code, String libelle, String libelleEn, int coefficient, LocalDate dtUpdate) {
        this.id = id;
        this.code = code;
        this.libelle = libelle;
        this.libelleEn = libelleEn;
        this.coefficient = coefficient;
        this.dtUpdate = dtUpdate;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getLibelle() {
        return libelle;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    public String getLibelleEn() {
        return libelleEn;
    }

    public void setLibelleEn(String libelleEn) {
        this.libelleEn = libelleEn;
    }

    public int getCoefficient() {
        return coefficient;
    }

    public void setCoefficient(int coefficient) {
        this.coefficient = coefficient;
    }

    public LocalDate getDtUpdate() {
        return dtUpdate;
    }

    public void setDtUpdate(LocalDate dtUpdate) {
        this.dtUpdate = dtUpdate;
    }
}
