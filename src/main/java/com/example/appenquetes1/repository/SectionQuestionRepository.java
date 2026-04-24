package com.example.appenquetes1.repository;

import com.example.appenquetes1.entity.SectionQuestion;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface SectionQuestionRepository extends JpaRepository<SectionQuestion, Integer> {
    List<SectionQuestion> findByOrdre(Integer ordre);

    List<SectionQuestion> findByQuestionId(Integer questionId);

    @Modifying
    @Transactional
    @Query("DELETE FROM SectionQuestion sq WHERE sq.question.id = :questionId")
    void deleteByQuestionId(@Param("questionId") Integer questionId);
}

