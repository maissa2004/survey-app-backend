package com.example.appenquetes1.repository;

import com.example.appenquetes1.entity.QuestionAnswers;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface QuestionAnswersRepository extends JpaRepository<QuestionAnswers, Integer> {
    List<QuestionAnswers> findByIsConditionnel(Boolean conditionnel);

}

