package com.example.appenquetes1.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "question")
public class Question {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String code;
    private String titleFr;
    private String titleEn;
    private Boolean isRequired;
    private Boolean isConditionnel;

    @ManyToOne
    @JoinColumn(name = "section_id")
    @JsonIgnore
    private Section section;

    // 🔥 relation vers Answer
    @OneToMany(mappedBy = "question", cascade = CascadeType.ALL)
    private List<QuestionAnswers> answers;

    // 🔥 relation récursive via Answer
    @ManyToOne
    @JoinColumn(name = "parent_answer_id")
    @JsonIgnore
    private QuestionAnswers parentAnswer;

    @ManyToOne
    @JoinColumn(name = "id_nm_type_quest")
    private NmTypeQuest nmtypeQuest;

    @ManyToOne
    @JoinColumn(name = "id_section_question")
    private SectionQuestion sectionQuestion;

    public SectionQuestion getSectionQuestion() {
        return sectionQuestion;
    }

    public void setSectionQuestion(SectionQuestion sectionQuestion) {
        this.sectionQuestion = sectionQuestion;
    }


    public NmTypeQuest getNmtypeQuest() {
        return nmtypeQuest;
    }

    public void setNmtypeQuest(NmTypeQuest nmtypeQuest) {
        this.nmtypeQuest = nmtypeQuest;
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

    public String getTitleFr() {
        return titleFr;
    }

    public void setTitleFr(String titleFr) {
        this.titleFr = titleFr;
    }

    public String getTitleEn() {
        return titleEn;
    }

    public void setTitleEn(String titleEn) {
        this.titleEn = titleEn;
    }

    public Boolean getRequired() {
        return isRequired;
    }

    public void setRequired(Boolean required) {
        isRequired = required;
    }

    public Boolean getConditionnel() {
        return isConditionnel;
    }

    public void setConditionnel(Boolean conditionnel) {
        isConditionnel = conditionnel;
    }



    public Section getSection() {
        return section;
    }

    public void setSection(Section section) {
        this.section = section;
    }

    public List<QuestionAnswers> getAnswers() {
        return answers;
    }

    public void setAnswers(List<QuestionAnswers> answers) {
        this.answers = answers;
    }

    public QuestionAnswers getParentAnswer() {
        return parentAnswer;
    }

    public void setParentAnswer(QuestionAnswers parentAnswer) {
        this.parentAnswer = parentAnswer;
    }
}

