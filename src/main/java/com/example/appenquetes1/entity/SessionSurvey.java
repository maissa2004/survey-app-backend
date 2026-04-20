// com.example.appenquetes1.entity.SessionSurvey.java
package com.example.appenquetes1.entity;

import jakarta.persistence.*;
import jakarta.websocket.Session;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "session_survey")
public class SessionSurvey {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "id_session", nullable = false)
    private Integer idSession;

    @Column(name = "id_survey", nullable = false)
    private Integer idSurvey;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_session", insertable = false, updatable = false)
    private Session session;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_survey", insertable = false, updatable = false)
    private Survey survey;

    // ✅ AJOUTER LA RELATION AVEC LES ENQUÊTEURS
    @OneToMany(mappedBy = "sessionSurvey", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<SessionEnqueteur> enqueteurs = new ArrayList<>();

    // Constructeurs
    public SessionSurvey() {}

    public SessionSurvey(Integer idSession, Integer idSurvey) {
        this.idSession = idSession;
        this.idSurvey = idSurvey;
    }

    // Getters et Setters
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public Integer getIdSession() { return idSession; }
    public void setIdSession(Integer idSession) { this.idSession = idSession; }

    public Integer getIdSurvey() { return idSurvey; }
    public void setIdSurvey(Integer idSurvey) { this.idSurvey = idSurvey; }

    public Session getSession() { return session; }
    public void setSession(Session session) { this.session = session; }

    public Survey getSurvey() { return survey; }
    public void setSurvey(Survey survey) { this.survey = survey; }

    public List<SessionEnqueteur> getEnqueteurs() { return enqueteurs; }
    public void setEnqueteurs(List<SessionEnqueteur> enqueteurs) { this.enqueteurs = enqueteurs; }

}