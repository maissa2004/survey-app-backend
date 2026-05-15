package com.example.appenquetes1.repository;

import com.example.appenquetes1.entity.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface QuestionRepository extends JpaRepository<Question, Integer> {
    List<Question> findByTitleFr(String titleFr);
    Optional<Question> findByCode(String code);

    // Dans QuestionRepository.java
    @Query("SELECT q FROM Question q WHERE q.parentAnswer.question.id = :questionId")
    List<Question> findByParentAnswerQuestionId(@Param("questionId") Integer questionId);}
