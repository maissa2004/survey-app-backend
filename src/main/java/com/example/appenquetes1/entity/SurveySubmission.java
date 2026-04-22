package com.example.appenquetes1.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "survey_submission")
public class SurveySubmission {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "id_user", nullable = false)
    private Integer userId;

    @Column(name = "id_survey", nullable = false)
    private Integer surveyId;

    @Column(name = "submission_date", nullable = false)
    private LocalDateTime submissionDate;

    @Column(nullable = false, columnDefinition = "ENUM('EN ATTENTE','ACCEPTE','REJETE')")
    private String status;

    // Constructeurs
    public SurveySubmission() {}

    public SurveySubmission(Integer userId, Integer surveyId, LocalDateTime submissionDate, String status) {
        this.userId = userId;
        this.surveyId = surveyId;
        this.submissionDate = submissionDate;
        this.status = status;
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
}
