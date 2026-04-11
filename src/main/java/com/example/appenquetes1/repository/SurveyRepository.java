package com.example.appenquetes1.repository;

import com.example.appenquetes1.entity.Survey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface SurveyRepository extends JpaRepository<Survey, Integer> {

    @Query("SELECT DISTINCT s FROM Survey s LEFT JOIN FETCH s.sections WHERE s.id = :id")
    Optional<Survey> findSurveyWithSections(@Param("id") Integer id);

    @Query("""
SELECT DISTINCT s FROM Survey s
LEFT JOIN FETCH s.sections sec
LEFT JOIN FETCH sec.sectionQuestions sq
LEFT JOIN FETCH sq.question q
LEFT JOIN FETCH q.nmtypeQuest
LEFT JOIN FETCH q.answers qa  
LEFT JOIN FETCH qa.nmAnswers
LEFT JOIN FETCH qa.condiQuestions cq  
LEFT JOIN FETCH cq.answers cqa  
LEFT JOIN FETCH cqa.nmAnswers
LEFT JOIN FETCH qa.condiSections cs
WHERE s.id = :id
""")

    Optional<Survey> findFullSurvey(@Param("id") Integer id);
    List<Survey> findByIdUser(Integer userId);

    List<Survey> findByIdUserAndIsValid(Integer userId, boolean isValid);

    @Query("SELECT s FROM Survey s WHERE s.idUser = :userId ORDER BY s.dtUpdate DESC")
    List<Survey> findSurveysByUserOrderByDate(@Param("userId") Integer userId);

    @Query("SELECT DISTINCT s FROM Survey s LEFT JOIN FETCH s.sections WHERE s.idUser = :userId")
    List<Survey> findSurveysByUserWithSections(@Param("userId") Integer userId);
}