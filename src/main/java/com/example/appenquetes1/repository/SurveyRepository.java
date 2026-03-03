package com.example.appenquetes1.repository;

import com.example.appenquetes1.entity.Survey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface SurveyRepository extends JpaRepository<Survey, Integer> {

    @Query("SELECT DISTINCT s FROM Survey s LEFT JOIN FETCH s.sections WHERE s.id = :id")
    Optional<Survey> findSurveyWithSections(@Param("id") Integer id);

    @Query("""
    SELECT DISTINCT s FROM Survey s
    LEFT JOIN FETCH s.sections sec
    LEFT JOIN FETCH sec.sectionQuestions sq
    LEFT JOIN FETCH sq.question q
    LEFT JOIN FETCH sq.questionAnswers qa
    LEFT JOIN FETCH qa.nmAnswers ans
    WHERE s.id = :id
""")
    Optional<Survey> findFullSurvey(@Param("id") Integer id);

}

