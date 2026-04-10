package com.example.appenquetes1.service;

import com.example.appenquetes1.entity.NmAnswers;
import com.example.appenquetes1.entity.Survey;
import com.example.appenquetes1.repository.NmAnswersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NmAnswersService {

    @Autowired
    private NmAnswersRepository repository;

    public NmAnswers save(NmAnswers a) {
        NmAnswers saved = repository.save(a);
        System.out.println("Service - ID généré: " + saved.getId());
        return saved;
    }

    public List<NmAnswers> findAll() {
        return repository.findAll();
    }

    public NmAnswers findById(Integer id) {
        return repository.findById(id).orElse(null);
    }

    public void delete(Integer id) {
        repository.deleteById(id);
    }

    public List<NmAnswers> findByLibelle(String libelle) {
        return repository.findByLibelle(libelle);
    }
}

