package com.example.appenquetes1.dto.Answer;

import com.example.appenquetes1.dto.question.QuestionResponseDTO;
import com.example.appenquetes1.dto.section.SectionResponseDTO;

import java.time.LocalDate;
import java.util.List;

public class AnswerResponseDTO {
    private Integer id;
    private String code;
    private String reference;
    private String libelle;
    private String libelleEn;
    private LocalDate dtUpdate;

    private List<QuestionResponseDTO> condiQuestion;
    private List<SectionResponseDTO> condiSections;

    public AnswerResponseDTO() {}

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

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
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

    public LocalDate getDtUpdate() {
        return dtUpdate;
    }

    public void setDtUpdate(LocalDate dtUpdate) {
        this.dtUpdate = dtUpdate;
    }

    public List<QuestionResponseDTO> getCondiQuestion() {
        return condiQuestion;
    }

    public void setCondiQuestion(List<QuestionResponseDTO> condiQuestion) {
        this.condiQuestion = condiQuestion;
    }

    public List<SectionResponseDTO> getCondiSections() {
        return condiSections;
    }

    public void setCondiSections(List<SectionResponseDTO> condiSections) {
        this.condiSections = condiSections;
    }
}