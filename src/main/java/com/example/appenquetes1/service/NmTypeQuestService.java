package com.example.appenquetes1.service;

import com.example.appenquetes1.entity.NmTypeQuest;
import com.example.appenquetes1.repository.NmTypeQuestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NmTypeQuestService {

    @Autowired
    private NmTypeQuestRepository repository;

    public NmTypeQuest save(NmTypeQuest t) {
        return repository.save(t);
    }

    public List<NmTypeQuest> findAll() {
        return repository.findAll();
    }

    public NmTypeQuest findById(Integer id) {
        return repository.findById(id).orElse(null);
    }

    public void delete(Integer id) {
        repository.deleteById(id);
    }

    public List<NmTypeQuest> findByLibelle(String libelle) {
        return repository.findByLibelle(libelle);
    }
}

