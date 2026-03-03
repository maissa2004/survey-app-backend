package com.example.appenquetes1.dto.Answer;

import com.example.appenquetes1.dto.question.QuestionResponseDTO;

import java.time.LocalDate;
import java.util.List;

public class AnswerResponseDTO {
    private Integer id; // id de QuestionAnswers
    private String code; // NmAnswers.code
    private String reference;
    private String libelle;
    private String libelleEn;
    private LocalDate dtUpdate;

    private List<QuestionResponseDTO> condiQuestion;

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

    // getters & setters
}
