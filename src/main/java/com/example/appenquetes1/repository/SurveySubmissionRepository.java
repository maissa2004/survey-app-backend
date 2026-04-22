package com.example.appenquetes1.repository;

import com.example.appenquetes1.entity.SurveySubmission;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface SurveySubmissionRepository extends JpaRepository<SurveySubmission, Integer> {
    long countByUserIdAndSurveyId(Integer userId, Integer surveyId);
    List<SurveySubmission> findByUserIdAndSurveyId(Integer userId, Integer surveyId);
}
