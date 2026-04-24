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

    @Modifying
    @Transactional
    @Query("DELETE FROM SessionSurvey ss WHERE ss.idSession = :sessionId")
    void deleteBySessionId(@Param("sessionId") Integer sessionId);

    boolean existsByIdSessionAndIdSurvey(Integer sessionId, Integer surveyId);
}