package com.example.appenquetes1.dto.Answer;

import java.time.LocalDate;
import java.util.List;

public class AnswerCreateDTO {
    private Integer id;
    private String code;
    private String libelle;
    private String libelleEn;
    private String reference;
    private LocalDate dtUpdate;
    private Boolean isConditionnel;
    private List<Integer> condiQuestionIds;
    private List<Integer> condiSectionIds;
    public AnswerCreateDTO() {
    }

    public AnswerCreateDTO(Integer id, String code, String libelle, String libelleEn, String reference, LocalDate dtUpdate) {
        this.id = id;
        this.code = code;
        this.libelle = libelle;
        this.libelleEn = libelleEn;
        this.reference = reference;
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

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public LocalDate getDtUpdate() {
        return dtUpdate;
    }

    public void setDtUpdate(LocalDate dtUpdate) {
        this.dtUpdate = dtUpdate;
    }

    public Boolean getIsConditionnel() {
        return isConditionnel;
    }

    public void setIsConditionnel(Boolean conditionnel) {
        isConditionnel = conditionnel;
    }

    public List<Integer> getCondiQuestionIds() {
        return condiQuestionIds;
    }

    public void setCondiQuestionIds(List<Integer> condiQuestionIds) {
        this.condiQuestionIds = condiQuestionIds;
    }

    public List<Integer> getCondiSectionIds() {
        return condiSectionIds;
    }

    public void setCondiSectionIds(List<Integer> condiSectionIds) {
        this.condiSectionIds = condiSectionIds;
    }
}
