package com.example.appenquetes1.dto.survey;

import com.example.appenquetes1.dto.etatSurvey.EtatSurveyResponseDTO;
import com.example.appenquetes1.dto.section.SectionResponseDTO;

import java.time.LocalDate;
import java.util.List;

public class SurveyResponseDTO {

    private Integer id;
    private String code;
    private String libelle;
    private String libelleEn;
    private LocalDate dtAdd;
    private LocalDate dtUpdate;
    private boolean isFormReference;
<<<<<<< Updated upstream
=======
    private Integer idEtatSurvey;
>>>>>>> Stashed changes

    private List<SectionResponseDTO> sections;

    public SurveyResponseDTO() {}

    public SurveyResponseDTO(Integer id, String code, String libelle,
                             String libelleEn, LocalDate dtAdd,
                             LocalDate dtUpdate, boolean isValid,
                             boolean isFormReference,
                             List<SectionResponseDTO> sections) {

        this.id = id;
        this.code = code;
        this.libelle = libelle;
        this.libelleEn = libelleEn;
        this.dtAdd = dtAdd;
        this.dtUpdate = dtUpdate;
        this.isFormReference = isFormReference;
        this.sections = sections;
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

    public boolean isFormReference() {
        return isFormReference;
    }

    public void setFormReference(boolean formReference) {
        isFormReference = formReference;
    }

    public List<SectionResponseDTO> getSections() {
        return sections;
    }

    public void setSections(List<SectionResponseDTO> sections) {
        this.sections = sections;
    }

<<<<<<< Updated upstream
    // getters & setters
=======
    public Integer getIdEtatSurvey() { return idEtatSurvey; }
    public void setIdEtatSurvey(Integer idEtatSurvey) { this.idEtatSurvey = idEtatSurvey; }
>>>>>>> Stashed changes
}
