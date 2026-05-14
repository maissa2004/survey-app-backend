package com.example.appenquetes1.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "survey_submission")
public class SurveySubmission {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "id_user", nullable = false)
    private Integer userId;


    @Column(name = "id_survey")
    private Integer surveyId;

    @Column(name = "submission_date", nullable = false)
    private LocalDateTime submissionDate;

    @Column(nullable = false, columnDefinition = "ENUM('EN ATTENTE','ACCEPTE','REJETE')")
    private String status;
    @Column(name = "validation_comment", columnDefinition = "TEXT")
    private String validationComment;

    @Column(name = "validated_by")
    private Integer validatedBy;

    @Column(name = "validated_at")
    private LocalDateTime validatedAt;

    @OneToMany (mappedBy = "submission")
    private List<Answers> submissionAnswers;

    // Constructeurs
    public SurveySubmission(List<Answers> submissionAnswers) {
        this.submissionAnswers = submissionAnswers;
    }

    public SurveySubmission(Integer userId, Integer surveyId , LocalDateTime submissionDate, String status, List<Answers> submissionAnswers) {
        this.userId = userId;
        this.surveyId = surveyId;
        this.submissionDate = submissionDate;
        this.status = status;
        this.submissionAnswers = submissionAnswers;
    }

    public SurveySubmission(Integer id, LocalDateTime validatedAt, Integer validatedBy, String validationComment, String status, LocalDateTime submissionDate, Integer surveyId, Integer userId, List<Answers> submissionAnswers) {
        this.id = id;
        this.validatedAt = validatedAt;
        this.validatedBy = validatedBy;
        this.validationComment = validationComment;
        this.status = status;
        this.submissionDate = submissionDate;
        this.surveyId = surveyId;
        this.userId = userId;
        this.submissionAnswers = submissionAnswers;
    }

    public SurveySubmission() {

    }

    // Getters et Setters
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public Integer getUserId() { return userId; }
    public void setUserId(Integer userId) { this.userId = userId; }

    public Integer getSurveyId() { return surveyId; }
    public void setSurveyId(Integer surveyId) { this.surveyId = surveyId; }

    public LocalDateTime getSubmissionDate() { return submissionDate; }
    public void setSubmissionDate(LocalDateTime submissionDate) { this.submissionDate = submissionDate; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public String getValidationComment() {
        return validationComment;
    }

    public void setValidationComment(String validationComment) {
        this.validationComment = validationComment;
    }

    public Integer getValidatedBy() {
        return validatedBy;
    }

    public void setValidatedBy(Integer validatedBy) {
        this.validatedBy = validatedBy;
    }

    public LocalDateTime getValidatedAt() {
        return validatedAt;
    }

    public void setValidatedAt(LocalDateTime validatedAt) {
        this.validatedAt = validatedAt;
    }
}
