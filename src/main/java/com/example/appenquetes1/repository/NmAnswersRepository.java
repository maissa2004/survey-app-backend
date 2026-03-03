package com.example.appenquetes1.repository;

import com.example.appenquetes1.entity.NmAnswers;
import com.example.appenquetes1.entity.Survey;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface NmAnswersRepository extends JpaRepository<NmAnswers, Integer> {
    List<NmAnswers> findByLibelle(String libelle);

}
