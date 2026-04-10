package com.example.appenquetes1.repository;

import com.example.appenquetes1.entity.QuestionAnswers;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;

import java.util.List;

public interface QuestionAnswersRepository extends JpaRepository<QuestionAnswers, Integer> {
    List<QuestionAnswers> findByIsConditionnel(Boolean conditionnel);
    List<QuestionAnswers> findByQuestionId(Integer questionId);
    @Modifying
    @Transactional
    int deleteByQuestionId(Integer questionId);
}

