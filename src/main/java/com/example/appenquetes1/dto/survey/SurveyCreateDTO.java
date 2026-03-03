package com.example.appenquetes1.dto.survey;

import com.example.appenquetes1.dto.etatSurvey.EtatSurveyCreateDTO;
import com.example.appenquetes1.dto.section.SectionCreateDTO;
import lombok.Getter;

import java.time.LocalDate;
import java.util.List;

public class SurveyCreateDTO {
    @Getter
    private Integer id;
    private String code;
    private String libelle;
    private String libelleEn;
    private LocalDate dtAdd;
    private LocalDate dtUpdate;
    private boolean isValid;
    private boolean isFormReference;
    private List<SectionCreateDTO> sections;



    public SurveyCreateDTO() {}

    public SurveyCreateDTO(Integer id, String code, String libelle, String libelleEn, LocalDate dtAdd, LocalDate dtUpdate, boolean isValid, boolean isFormReference, List<SectionCreateDTO> sections , EtatSurveyCreateDTO etatSurvey) {
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

    public LocalDate getDtAdd() {
        return dtAdd;
    }

    public void setDtAdd(LocalDate dtAdd) {
        this.dtAdd = dtAdd;
    }

    public LocalDate getDtUpdate() {
        return dtUpdate;
    }

    public void setDtUpdate(LocalDate dtUpdate) {
        this.dtUpdate = dtUpdate;
    }

    public boolean isValid() {
        return isValid;
    }

    public void setValid(boolean valid) {
        isValid = valid;
    }

    public boolean isFormReference() {
        return isFormReference;
    }

    public void setFormReference(boolean formReference) {
        isFormReference = formReference;
    }

    public List<SectionCreateDTO> getSections() {
        return sections;
    }

    public void setSections(List<SectionCreateDTO> sections) {
        this.sections = sections;
    }

    }
