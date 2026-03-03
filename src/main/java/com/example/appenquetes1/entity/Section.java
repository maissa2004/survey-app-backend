package com.example.appenquetes1.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "section")
public class Section {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String code;
    private String title;
    private String titleEn;
    private boolean isConditionnel;
    private int ordre;
    private Integer idReferencedForm;
    private LocalDate dtUpdate;

    public Section() {
    }

    public Section(Integer id, String code, String title, String titleEn, boolean isConditionnel, int ordre, Integer idReferencedForm, LocalDate dtUpdate, Survey survey) {
        this.id = id;
        this.code = code;
        this.title = title;
        this.titleEn = titleEn;
        this.isConditionnel = isConditionnel;
        this.ordre = ordre;
        this.idReferencedForm = idReferencedForm;
        this.dtUpdate = dtUpdate;
        this.survey = survey;
    }

    @ManyToOne
    @JoinColumn(name = "id_survey")
    @JsonIgnore
    private Survey survey;


    @OneToMany(mappedBy = "section", cascade = CascadeType.ALL)
    private Set<SectionQuestion> sectionQuestions = new HashSet<>();

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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitleEn() {
        return titleEn;
    }

    public void setTitleEn(String titleEn) {
        this.titleEn = titleEn;
    }

    public boolean isConditionnel() {
        return isConditionnel;
    }

    public void setConditionnel(boolean conditionnel) {
        isConditionnel = conditionnel;
    }

    public int getOrdre() {
        return ordre;
    }

    public void setOrdre(int ordre) {
        this.ordre = ordre;
    }

    public Integer getIdReferencedForm() {
        return idReferencedForm;
    }

    public void setIdReferencedForm(Integer idReferencedForm) {
        this.idReferencedForm = idReferencedForm;
    }

    public LocalDate getDtUpdate() {
        return dtUpdate;
    }

    public void setDtUpdate(LocalDate dtUpdate) {
        this.dtUpdate = dtUpdate;
    }

    public Survey getSurvey() {
        return survey;
    }

    public void setSurvey(Survey survey) {
        this.survey = survey;
    }

    public Set<SectionQuestion> getSectionQuestions() {
        return sectionQuestions;
    }

    public void setSectionQuestions(Set<SectionQuestion> sectionQuestions) {
        this.sectionQuestions = sectionQuestions;
    }
}

