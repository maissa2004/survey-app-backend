package com.example.appenquetes1.repository;

import com.example.appenquetes1.entity.QuestionAnswers;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface QuestionAnswersRepository extends JpaRepository<QuestionAnswers, Integer> {
    List<QuestionAnswers> findByIsConditionnel(Boolean conditionnel);
    List<QuestionAnswers> findByQuestionId(Integer questionId);
    @Modifying
    @Transactional
    int deleteByQuestionId(Integer questionId);

    @Query("SELECT qa.nmAnswers.id FROM QuestionAnswers qa WHERE qa.question.id = :questionId")
    List<Integer> findNmAnswerIdsByQuestionId(@Param("questionId") Integer questionId);
}

