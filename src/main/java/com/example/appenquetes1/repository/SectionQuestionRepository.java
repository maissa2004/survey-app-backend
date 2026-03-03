package com.example.appenquetes1.repository;

import com.example.appenquetes1.entity.SectionQuestion;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SectionQuestionRepository extends JpaRepository<SectionQuestion, Integer> {
    List<SectionQuestion> findByOrdre(Integer ordre);

}

