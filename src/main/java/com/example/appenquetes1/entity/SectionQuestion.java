package com.example.appenquetes1.entity;

import com.fasterxml.jackson.annotation.*;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


@Entity
@Table(name = "section_question")
public class SectionQuestion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private boolean isRequired;
    private boolean isConditionnel;
    private int ordre;
    private String min;
    private String max;
    private String prefixe;
    private LocalDate dtUpdate;

    public SectionQuestion() {
    }

    public SectionQuestion(Integer id, boolean isRequired, boolean isConditionnel, int ordre, String min, String max, String prefixe, LocalDate dtUpdate, Section section, Question question) {
        this.id = id;
        this.isRequired = isRequired;
        this.isConditionnel = isConditionnel;
        this.ordre = ordre;
        this.min = min;
        this.max = max;
        this.prefixe = prefixe;
        this.dtUpdate = dtUpdate;
        this.section = section;
        this.question = question;
    }

    @ManyToOne
    @JoinColumn(name = "id_section")
    @JsonIgnore
    private Section section;

    @ManyToOne
    @JoinColumn(name = "id_question")
    @JsonIgnore
    private Question question;

    @OneToMany(mappedBy = "sectionQuestion")
    private Set<QuestionAnswers> questionAnswers = new HashSet<>();


    public Integer getId() { return id; }

    public void setId(Integer id) { this.id = id; }

    public boolean isRequired() {
        return isRequired;
    }

    public void setRequired(boolean required) {
        isRequired = required;
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

    public String getMin() {
        return min;
    }

    public void setMin(String min) {
        this.min = min;
    }

    public String getMax() {
        return max;
    }

    public void setMax(String max) {
        this.max = max;
    }

    public String getPrefixe() {
        return prefixe;
    }

    public void setPrefixe(String prefixe) {
        this.prefixe = prefixe;
    }

    public LocalDate getDtUpdate() {
        return dtUpdate;
    }

    public void setDtUpdate(LocalDate dtUpdate) {
        this.dtUpdate = dtUpdate;
    }

    public Section getSection() {
        return section;
    }

    public void setSection(Section section) {
        this.section = section;
    }

    public Question getQuestion() {
        return question;
    }

    public void setQuestion(Question question) {
        this.question = question;
    }

    public Set<QuestionAnswers> getQuestionAnswers() {
        return questionAnswers;
    }

    public void setQuestionAnswers(Set<QuestionAnswers> questionAnswers) {
        this.questionAnswers = questionAnswers;
    }
}

