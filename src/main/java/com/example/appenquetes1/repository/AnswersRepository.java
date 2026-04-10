// com.example.appenquetes1.repository.AnswersRepository.java
package com.example.appenquetes1.repository;

import com.example.appenquetes1.entity.Answers;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface AnswersRepository extends JpaRepository<Answers, Integer> {

    List<Answers> findByIdUser(Integer userId);

    List<Answers> findByIdSurvey(Integer surveyId);

    List<Answers> findByIdUserAndIdSurvey(Integer userId, Integer surveyId);

    List<Answers> findByIdSectionQuest(Integer sectionQuestId);

    List<Answers> findByIdUserAndCodeQuestion(Integer userId, String codeQuestion);

    Optional<Answers> findByIdUserAndIdSurveyAndIdSectionQuest(Integer userId, Integer surveyId, Integer sectionQuestId);

    @Modifying
    @Transactional
    @Query("DELETE FROM Answers a WHERE a.idUser = :userId AND a.idSurvey = :surveyId")
    void deleteByUserIdAndSurveyId(@Param("userId") Integer userId, @Param("surveyId") Integer surveyId);

    @Modifying
    @Transactional
    @Query("DELETE FROM Answers a WHERE a.idUser = :userId AND a.idSurvey = :surveyId AND a.idSectionQuest = :sectionQuestId")
    void deleteByUserIdSurveyIdAndSectionQuestId(@Param("userId") Integer userId,
                                                 @Param("surveyId") Integer surveyId,
                                                 @Param("sectionQuestId") Integer sectionQuestId);

    long countByIdUserAndIdSurvey(Integer userId, Integer surveyId);
}