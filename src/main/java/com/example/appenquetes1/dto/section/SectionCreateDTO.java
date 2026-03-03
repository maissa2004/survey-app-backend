package com.example.appenquetes1.dto.section;

import com.example.appenquetes1.dto.question.QuestionCreateDTO;
import com.example.appenquetes1.dto.sectionQuestion.SectionQuestionCreateDTO;

import java.time.LocalDate;
import java.util.List;

public class SectionCreateDTO {
    private Integer id;
    private String code;
    private String title;
    private String titleEn;
    private boolean isConditionnel;
    private int ordre;
    private Integer idReferencedForm;
    private LocalDate dtUpdate;

    private List<QuestionCreateDTO> questions;

    public SectionCreateDTO() {
    }

    public SectionCreateDTO(Integer id, String code, String title, String titleEn, boolean isConditionnel, int ordre, Integer idReferencedForm, LocalDate dtUpdate, List<QuestionCreateDTO> questions) {
        this.id = id;
        this.code = code;
        this.title = title;
        this.titleEn = titleEn;
        this.isConditionnel = isConditionnel;
        this.ordre = ordre;
        this.idReferencedForm = idReferencedForm;
        this.dtUpdate = dtUpdate;
        this.questions = questions;
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

    public List<QuestionCreateDTO> getQuestions() {
        return questions;
    }

    public void setQuestions(List<QuestionCreateDTO> sectionQuestions) {
        this.questions = questions;
    }
}
