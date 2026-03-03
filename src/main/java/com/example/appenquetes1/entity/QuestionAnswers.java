package com.example.appenquetes1.entity;

import com.fasterxml.jackson.annotation.*;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "question_answers")
public class QuestionAnswers {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String code;
    private String libelle;
    private String libelleEn;
    private String reference;
    private Boolean isConditionnel;

    private LocalDate dtUpdate;

    @ManyToOne
    @JoinColumn(name = "id_question")
    @JsonIgnore
    private Question question;

    @ManyToOne
    @JoinColumn(name = "id_nm_answers")
    private NmAnswers nmAnswers;

    @ManyToOne
    @JoinColumn(name = "id_section_question")
    private SectionQuestion sectionQuestion;

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

    public Question getQuestion() {
        return question;
    }

    public void setQuestion(Question question) {
        this.question = question;
    }


    public Boolean getIsConditionnel() {
        return isConditionnel;
    }

    public void setIsConditionnel(Boolean isConditionnel) {
        this.isConditionnel = isConditionnel;
    }
}
