package com.example.appenquetes1.dto.survey;

import java.time.LocalDate;

public class AssignedSurveyDTO {
    private Integer surveyId;
    private String surveyLibelle;
    private String surveyLibelleEn;
    private String sessionName;
    private LocalDate sessionStartDate;
    private LocalDate sessionEndDate;
    private long submissionCount;

    public Integer getSurveyId() {
        return surveyId;
    }

    public void setSurveyId(Integer surveyId) {
        this.surveyId = surveyId;
    }

    public String getSurveyLibelle() {
        return surveyLibelle;
    }

    public void setSurveyLibelle(String surveyLibelle) {
        this.surveyLibelle = surveyLibelle;
    }

    public String getSurveyLibelleEn() {
        return surveyLibelleEn;
    }

    public void setSurveyLibelleEn(String surveyLibelleEn) {
        this.surveyLibelleEn = surveyLibelleEn;
    }

    public String getSessionName() {
        return sessionName;
    }

    public void setSessionName(String sessionName) {
        this.sessionName = sessionName;
    }

    public LocalDate getSessionStartDate() {
        return sessionStartDate;
    }

    public void setSessionStartDate(LocalDate sessionStartDate) {
        this.sessionStartDate = sessionStartDate;
    }

    public LocalDate getSessionEndDate() {
        return sessionEndDate;
    }

    public void setSessionEndDate(LocalDate sessionEndDate) {
        this.sessionEndDate = sessionEndDate;
    }

    public long getSubmissionCount() {
        return submissionCount;
    }

    public void setSubmissionCount(long submissionCount) {
        this.submissionCount = submissionCount;
    }
}
