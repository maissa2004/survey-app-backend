// com.example.appenquetes1.entity.Session.java
package com.example.appenquetes1.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "session")
public class Session {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, length = 255)
    private String intitule;

    @Column(name = "date_debut", nullable = false)
    private LocalDateTime dateDebut;

    @Column(name = "date_fin", nullable = false)
    private LocalDateTime dateFin;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, columnDefinition = "ENUM('active', 'inactive', 'planifiee', 'terminee') DEFAULT 'planifiee'")
    private Status status = Status.planifiee;

    @Column(name = "dt_create")
    private LocalDateTime dtCreate;

    @Column(name = "dt_update")
    private LocalDateTime dtUpdate;

    // Relation OneToMany avec la table de liaison
    @OneToMany(mappedBy = "session", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<SessionSurvey> sessionSurveys = new ArrayList<>();

    public enum Status {
        active, inactive, planifiee, terminee
    }

    @PrePersist
    protected void onCreate() {
        dtCreate = LocalDateTime.now();
        dtUpdate = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        dtUpdate = LocalDateTime.now();
    }

    // Constructeurs
    public Session() {}

    public Session(String intitule, LocalDateTime dateDebut, LocalDateTime dateFin) {
        this.intitule = intitule;
        this.dateDebut = dateDebut;
        this.dateFin = dateFin;
        this.status = Status.planifiee;
    }

    // Getters et Setters
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public String getIntitule() { return intitule; }
    public void setIntitule(String intitule) { this.intitule = intitule; }

    public LocalDateTime getDateDebut() { return dateDebut; }
    public void setDateDebut(LocalDateTime dateDebut) { this.dateDebut = dateDebut; }

    public LocalDateTime getDateFin() { return dateFin; }
    public void setDateFin(LocalDateTime dateFin) { this.dateFin = dateFin; }

    public Status getStatus() { return status; }
    public void setStatus(Status status) { this.status = status; }

    public LocalDateTime getDtCreate() { return dtCreate; }
    public void setDtCreate(LocalDateTime dtCreate) { this.dtCreate = dtCreate; }

    public LocalDateTime getDtUpdate() { return dtUpdate; }
    public void setDtUpdate(LocalDateTime dtUpdate) { this.dtUpdate = dtUpdate; }

    public List<SessionSurvey> getSessionSurveys() { return sessionSurveys; }
    public void setSessionSurveys(List<SessionSurvey> sessionSurveys) { this.sessionSurveys = sessionSurveys; }
}