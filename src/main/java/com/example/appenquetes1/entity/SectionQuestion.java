package com.example.appenquetes1.entity;

import com.fasterxml.jackson.annotation.*;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.HashSet;
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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_answer_id", insertable = false, updatable = false)
    private QuestionAnswers parentAnswer;

    // Getters et Setters

    public void setIdSection(Integer idSection) {
        if (idSection != null) {
            this.section = new Section();
            this.section.setId(idSection);
        }
    }

    public void setIdQuestion(Integer idQuestion) {
        if (idQuestion != null) {
            this.question = new Question();
            this.question.setId(idQuestion);
        }
    }

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public boolean isRequired() { return isRequired; }
    public void setRequired(boolean required) { isRequired = required; }

    public boolean isConditionnel() { return isConditionnel; }
    public void setConditionnel(boolean conditionnel) { isConditionnel = conditionnel; }

    public int getOrdre() { return ordre; }
    public void setOrdre(int ordre) { this.ordre = ordre; }

    public String getMin() { return min; }
    public void setMin(String min) { this.min = min; }

    public String getMax() { return max; }
    public void setMax(String max) { this.max = max; }

    public String getPrefixe() { return prefixe; }
    public void setPrefixe(String prefixe) { this.prefixe = prefixe; }

    public LocalDate getDtUpdate() { return dtUpdate; }
    public void setDtUpdate(LocalDate dtUpdate) { this.dtUpdate = dtUpdate; }

    public Section getSection() { return section; }
    public void setSection(Section section) { this.section = section; }

    public Question getQuestion() { return question; }
    public void setQuestion(Question question) { this.question = question; }

    public Set<QuestionAnswers> getQuestionAnswers() { return questionAnswers; }
    public void setQuestionAnswers(Set<QuestionAnswers> questionAnswers) {
        this.questionAnswers = questionAnswers;
    }

    public QuestionAnswers getParentAnswer() { return parentAnswer; }
    public void setParentAnswer(QuestionAnswers parentAnswer) {
        this.parentAnswer = parentAnswer;
    }
}