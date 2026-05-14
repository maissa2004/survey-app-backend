package com.example.appenquetes1.dto.admin;

import java.time.LocalDateTime;
import java.util.List;

public class SubmissionAdminDTO {
    private Integer id;
    private Integer surveyId;
    private String surveyLibelle;
    private Integer userId;
    private String username;
    private LocalDateTime submissionDate;
    private String status;
    private String validationComment;
    private Integer validatedBy;
    private String validatedByUsername;
    private LocalDateTime validatedAt;
    private List<AnswerDetailDTO> answers;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public List<AnswerDetailDTO> getAnswers() {
        return answers;
    }

    public void setAnswers(List<AnswerDetailDTO> answers) {
        this.answers = answers;
    }

    public LocalDateTime getValidatedAt() {
        return validatedAt;
    }

    public void setValidatedAt(LocalDateTime validatedAt) {
        this.validatedAt = validatedAt;
    }

    public Integer getValidatedBy() {
        return validatedBy;
    }

    public void setValidatedBy(Integer validatedBy) {
        this.validatedBy = validatedBy;
    }

    public String getValidationComment() {
        return validationComment;
    }

    public void setValidationComment(String validationComment) {
        this.validationComment = validationComment;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDateTime getSubmissionDate() {
        return submissionDate;
    }

    public void setSubmissionDate(LocalDateTime submissionDate) {
        this.submissionDate = submissionDate;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getSurveyLibelle() {
        return surveyLibelle;
    }

    public void setSurveyLibelle(String surveyLibelle) {
        this.surveyLibelle = surveyLibelle;
    }

    public Integer getSurveyId() {
        return surveyId;
    }

    public void setSurveyId(Integer surveyId) {
        this.surveyId = surveyId;
    }

    public void setValidatedByUsername(String validatedByUsername) {
        this.validatedByUsername = validatedByUsername;
    }

    public String getValidatedByUsername() {
        return validatedByUsername;
    }
}