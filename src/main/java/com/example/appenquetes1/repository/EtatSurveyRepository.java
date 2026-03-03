package com.example.appenquetes1.repository;

import com.example.appenquetes1.entity.EtatSurvey;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EtatSurveyRepository extends JpaRepository<EtatSurvey, Integer> {
    List<EtatSurvey> findByEtat(Boolean etat);

}

