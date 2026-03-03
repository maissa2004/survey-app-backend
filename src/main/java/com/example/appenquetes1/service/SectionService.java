package com.example.appenquetes1.service;

import com.example.appenquetes1.entity.Section;
import com.example.appenquetes1.entity.SectionQuestion;
import com.example.appenquetes1.repository.SectionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SectionService {

    @Autowired
    private SectionRepository repository;

    public Section save(Section s) {
        return repository.save(s);
    }

    public List<Section> findAll() {
        return repository.findAll();
    }

    public Section findById(Integer id) {
        return repository.findById(id).orElse(null);
    }

    public void delete(Integer id) {
        repository.deleteById(id);
    }

    public List<Section> findByTitle(String title) {
        return repository.findByTitle(title);
    }

    /*public List<SectionQuestion> findQuestionsBySectionId(Integer sectionId) {

        Section section = findById(sectionId);

        return section.getSectionQuestions();
    }*/

    public Section getFullSection(Integer id) {
        return repository.findFullSection(id)
                .orElseThrow(() -> new RuntimeException("Section introuvable"));
    }

}

