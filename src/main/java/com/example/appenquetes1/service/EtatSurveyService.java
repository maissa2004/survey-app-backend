package com.example.appenquetes1.service;

import com.example.appenquetes1.entity.EtatSurvey;
import com.example.appenquetes1.repository.EtatSurveyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EtatSurveyService {

    @Autowired
    private EtatSurveyRepository repository;

    public EtatSurvey save(EtatSurvey e) {
        return repository.save(e);
    }

    public List<EtatSurvey> findAll() {
        return repository.findAll();
    }

    public EtatSurvey findById(Integer id) {
        return repository.findById(id).orElse(null);
    }

    public void delete(Integer id) {
        repository.deleteById(id);
    }

    public List<EtatSurvey> findByEtat(Boolean etat) {
        return repository.findByEtat(etat);
    }
}

