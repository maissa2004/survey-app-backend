package com.example.appenquetes1.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "session_enqueteur")
public class SessionEnqueteur {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "id_session_survey", nullable = false)
    private Integer idSessionSurvey;

    @Column(name = "id_user", nullable = false)
    private Integer idUser;

    @Column(name = "date_affectation")
    private LocalDateTime dateAffectation;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_session_survey", insertable = false, updatable = false)
    private SessionSurvey sessionSurvey;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_user", insertable = false, updatable = false)
    private User user;

    @PrePersist
    protected void onCreate() {
        dateAffectation = LocalDateTime.now();
    }

    public SessionEnqueteur() {}

    public SessionEnqueteur(Integer idSessionSurvey, Integer idUser) {
        this.idSessionSurvey = idSessionSurvey;
        this.idUser = idUser;
    }

    // Getters et Setters
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public Integer getIdSessionSurvey() { return idSessionSurvey; }
    public void setIdSessionSurvey(Integer idSessionSurvey) { this.idSessionSurvey = idSessionSurvey; }

    public Integer getIdUser() { return idUser; }
    public void setIdUser(Integer idUser) { this.idUser = idUser; }

    public LocalDateTime getDateAffectation() { return dateAffectation; }
    public void setDateAffectation(LocalDateTime dateAffectation) { this.dateAffectation = dateAffectation; }

    public SessionSurvey getSessionSurvey() { return sessionSurvey; }
    public void setSessionSurvey(SessionSurvey sessionSurvey) { this.sessionSurvey = sessionSurvey; }

    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }
}