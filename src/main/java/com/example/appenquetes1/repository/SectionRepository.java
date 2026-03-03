package com.example.appenquetes1.repository;

import com.example.appenquetes1.entity.Section;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface SectionRepository extends JpaRepository<Section, Integer> {
    List<Section> findByTitle(String title);

    @Query("""
    SELECT DISTINCT s FROM Section s
    LEFT JOIN FETCH s.sectionQuestions sq
    LEFT JOIN FETCH sq.question q
    LEFT JOIN FETCH q.nmtypeQuest
    LEFT JOIN FETCH sq.questionAnswers qa
    LEFT JOIN FETCH qa.nmAnswers
    WHERE s.id = :id
""")
    Optional<Section> findFullSection(@Param("id") Integer id);
}

