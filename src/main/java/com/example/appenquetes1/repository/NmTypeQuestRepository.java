package com.example.appenquetes1.repository;

import com.example.appenquetes1.entity.NmTypeQuest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NmTypeQuestRepository extends JpaRepository<NmTypeQuest, Integer> {
    List<NmTypeQuest> findByLibelle(String libelle);

}
