package com.example.appenquetes1.repository;

import com.example.appenquetes1.entity.Session;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface SessionRepository extends JpaRepository<Session, Integer> {

    // Recherche par statut
    List<Session> findByStatus(Session.Status status);

    // Recherche par survey
    List<Session> findByIdSurvey(Integer surveyId);

    // Recherche par période
    List<Session> findByDateDebutBetween(LocalDateTime start, LocalDateTime end);

    // Sessions actives actuellement
    @Query("SELECT s FROM Session s WHERE s.status = 'active' AND s.dateDebut <= CURRENT_TIMESTAMP AND s.dateFin >= CURRENT_TIMESTAMP")
    List<Session> findCurrentActiveSessions();

    // Sessions à venir
    @Query("SELECT s FROM Session s WHERE s.status = 'planifiee' AND s.dateDebut > CURRENT_TIMESTAMP")
    List<Session> findUpcomingSessions();

    // Sessions terminées
    @Query("SELECT s FROM Session s WHERE s.status = 'terminee' OR s.dateFin < CURRENT_TIMESTAMP")
    List<Session> findFinishedSessions();

    // Vérifier si un survey a des sessions actives
    boolean existsByIdSurveyAndStatus(Integer surveyId, Session.Status status);

    // Mettre à jour le statut automatiquement
    @Modifying
    @Transactional
    @Query("UPDATE Session s SET s.status = 'terminee' WHERE s.status = 'active' AND s.dateFin < CURRENT_TIMESTAMP")
    void autoFinishExpiredSessions();

    // Activer une session
    @Modifying
    @Transactional
    @Query("UPDATE Session s SET s.status = 'active' WHERE s.id = :id AND s.status != 'terminee'")
    int activateSession(@Param("id") Integer id);

    // Désactiver une session
    @Modifying
    @Transactional
    @Query("UPDATE Session s SET s.status = 'inactive' WHERE s.id = :id AND s.status = 'active'")
    int deactivateSession(@Param("id") Integer id);

    // Compter les sessions actives par survey
    long countByIdSurveyAndStatus(Integer surveyId, Session.Status status);

    // Récupérer les sessions avec détails du survey
    @Query("SELECT s FROM Session s LEFT JOIN FETCH s.survey WHERE s.idSurvey = :surveyId")
    List<Session> findSessionsBySurveyWithDetails(@Param("surveyId") Integer surveyId);

    // Vérifier les conflits de dates pour un survey
    @Query("SELECT s FROM Session s WHERE s.idSurvey = :surveyId AND " +
            "((s.dateDebut BETWEEN :debut AND :fin) OR " +
            "(s.dateFin BETWEEN :debut AND :fin) OR " +
            "(:debut BETWEEN s.dateDebut AND s.dateFin))")
    List<Session> findOverlappingSessions(@Param("surveyId") Integer surveyId,
                                          @Param("debut") LocalDateTime debut,
                                          @Param("fin") LocalDateTime fin);
}