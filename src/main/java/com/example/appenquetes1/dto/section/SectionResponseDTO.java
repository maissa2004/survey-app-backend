package com.example.appenquetes1.dto.section;

import com.example.appenquetes1.dto.question.QuestionResponseDTO;

import java.time.LocalDate;
import java.util.List;

public class SectionResponseDTO {

    private Integer id;
    private String code;
    private String title;
    private String titleEn;
    private boolean isConditionnel;
    private int ordre;
    private Integer idSurvey;
    private LocalDate dtUpdate;

    private List<QuestionResponseDTO> questions;

    public SectionResponseDTO() {}

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

    public Integer getIdSurvey() {
        return idSurvey;
    }

    public void setIdSurvey(Integer idSurvey) {
        this.idSurvey = idSurvey;
    }

    public LocalDate getDtUpdate() {
        return dtUpdate;
    }

    public void setDtUpdate(LocalDate dtUpdate) {
        this.dtUpdate = dtUpdate;
    }

    public List<QuestionResponseDTO> getQuestions() {
        return questions;
    }

    public void setQuestions(List<QuestionResponseDTO> questions) {
        this.questions = questions;
    }

    // getters & setters
}
