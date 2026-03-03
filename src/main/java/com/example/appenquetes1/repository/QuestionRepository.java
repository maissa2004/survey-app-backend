package com.example.appenquetes1.repository;

import com.example.appenquetes1.entity.Question;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface QuestionRepository extends JpaRepository<Question, Integer> {
    List<Question> findByTitleFr(String titleFr);

}
