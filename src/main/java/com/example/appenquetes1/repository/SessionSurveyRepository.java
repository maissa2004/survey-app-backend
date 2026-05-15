// com.example.appenquetes1.repository.SessionSurveyRepository.java
package com.example.appenquetes1.repository;

import com.example.appenquetes1.entity.SessionSurvey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface SessionSurveyRepository extends JpaRepository<SessionSurvey, Integer> {

    List<SessionSurvey> findByIdSession(Integer sessionId);

    Optional<SessionSurvey> findBySurveyId(Integer surveyId);
    List<SessionSurvey> findAllBySurveyId(Integer surveyId);

    @Query("SELECT ss FROM SessionSurvey ss WHERE ss.idSession = :sessionId AND ss.idSurvey = :surveyId")
    Optional<SessionSurvey> findBySessionIdAndSurveyId(@Param("sessionId") Integer sessionId,
                                                       @Param("surveyId") Integer surveyId);

    @Modifying
    @Transactional
    @Query("DELETE FROM SessionSurvey ss WHERE ss.idSession = :sessionId")
    void deleteBySessionId(@Param("sessionId") Integer sessionId);

    @Query(value = "SELECT sess.intitule, COUNT(ss.id_survey) FROM session_survey ss INNER JOIN session sess ON ss.id_session= sess.id GROUP BY sess.intitule", nativeQuery = true)
    List<Object[]> countSurveysPerSession();
    boolean existsByIdSessionAndIdSurvey(Integer sessionId, Integer surveyId);
}