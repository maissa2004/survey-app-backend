package com.example.appenquetes1.entity;

import com.fasterxml.jackson.annotation.*;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "question_answers")
public class QuestionAnswers {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private Boolean isConditionnel;
    private LocalDate dtUpdate;

    @ManyToOne
    @JoinColumn(name = "id_question")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)  // ← CHANGEMENT ICI
    private Question question;

    @ManyToOne
    @JoinColumn(name = "id_nm_answers")
    private NmAnswers nmAnswers;

    @ManyToOne
    @JoinColumn(name = "id_section_quest")
    @JsonIgnore
    private SectionQuestion sectionQuestion;

    @OneToMany(mappedBy = "parentAnswer", fetch = FetchType.LAZY)
    private Set<Question> condiQuestions = new HashSet<>();

    @OneToMany(mappedBy = "parentAnswer", fetch = FetchType.LAZY)
    private Set<Section> condiSections = new HashSet<>();

    private Integer idQuestionTable;     // ← NOUVEAU
    private Integer idSectionCondition;

    // Getters et Setters
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public NmAnswers getNmAnswers() {
        return nmAnswers;
    }

    public void setNmAnswers(NmAnswers nmAnswers) {
        this.nmAnswers = nmAnswers;
    }

    public SectionQuestion getSectionQuestion() {
        return sectionQuestion;
    }

    public void setSectionQuestion(SectionQuestion sectionQuestion) {
        this.sectionQuestion = sectionQuestion;
    }

    public Set<Question> getCondiQuestions() {
        return condiQuestions;
    }

    public void setCondiQuestions(Set<Question> condiQuestions) {
        this.condiQuestions = condiQuestions;
    }

    public Set<Section> getCondiSections() {
        return condiSections;
    }

    public void setCondiSections(Set<Section> condiSections) {
        this.condiSections = condiSections;
    }
}