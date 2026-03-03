package com.example.appenquetes1.dto.sectionQuestion;

import com.example.appenquetes1.dto.question.QuestionCreateDTO;
import com.example.appenquetes1.dto.questionAnswers.QuestionAnswersCreateDTO;
import java.time.LocalDate;
import java.util.List;

public class SectionQuestionCreateDTO {
    private Integer id;
    private boolean isRequired;
    private boolean isConditionnel;
    private int ordre;
    private String min;
    private String max;
    private String prefixe;
    private LocalDate dtUpdate;
    private QuestionCreateDTO question;
    private List<QuestionAnswersCreateDTO> questionAnswers;

    public SectionQuestionCreateDTO() {
    }

    public SectionQuestionCreateDTO(Integer id, boolean isRequired, boolean isConditionnel, int ordre, String min, String max, String prefixe, LocalDate dtUpdate, List<QuestionAnswersCreateDTO> questionAnswers, QuestionCreateDTO question) {
        this.id = id;
        this.isRequired = isRequired;
        this.isConditionnel = isConditionnel;
        this.ordre = ordre;
        this.min = min;
        this.max = max;
        this.prefixe = prefixe;
        this.dtUpdate = dtUpdate;
        this.question = question;
        this.questionAnswers = questionAnswers;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

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

    public QuestionCreateDTO getQuestion() {
        return question;
    }

    public void setQuestion(QuestionCreateDTO question) {
        this.question = question;
    }

    public List<QuestionAnswersCreateDTO> getQuestionAnswers() {
        return questionAnswers;
    }

    public void setQuestionAnswers(List<QuestionAnswersCreateDTO> questionAnswers) {
        this.questionAnswers = questionAnswers;
    }
}
