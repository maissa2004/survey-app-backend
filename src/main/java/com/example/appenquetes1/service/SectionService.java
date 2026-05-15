package com.example.appenquetes1.service;

import com.example.appenquetes1.entity.Section;
import com.example.appenquetes1.entity.SectionQuestion;
import com.example.appenquetes1.entity.Survey;
import com.example.appenquetes1.repository.SectionRepository;
import com.example.appenquetes1.repository.SurveyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class SectionService {

    @Autowired
    private SectionRepository repository;

    public Section save(Section section) {
        // 🔥 Si c'est une nouvelle section (pas d'id) et ordre non défini
        if (section.getId() == null && (section.getOrdre() == 0 || section.getOrdre() == 1)) {
            // Calculer le prochain ordre
            Integer maxOrdre = repository.findMaxOrdreBySurvey(section.getSurvey().getId());
            int nextOrdre = (maxOrdre != null ? maxOrdre : 0) + 1;
            section.setOrdre(nextOrdre);
            System.out.println("📝 Ordre auto-calculé: " + nextOrdre);
        }

        section.setDtUpdate(LocalDate.now());
        return repository.save(section);
    }


    // 🔥 NOUVELLE MÉTHODE : Mise à jour sans écraser les relations
    public Section updateSection(Integer id, Section sectionData) {
        Section existingSection = repository.findById(id).orElse(null);
        if (existingSection == null) {
            return null;
        }

        // Mettre à jour uniquement les champs modifiables
        existingSection.setCode(sectionData.getCode());
        existingSection.setTitle(sectionData.getTitle());
        existingSection.setTitleEn(sectionData.getTitleEn());
        existingSection.setConditionnel(sectionData.isConditionnel());
        existingSection.setOrdre(sectionData.getOrdre());
        existingSection.setIdReferencedForm(sectionData.getIdReferencedForm());
        existingSection.setParentSectionId(sectionData.getParentSectionId());
        existingSection.setDtUpdate(LocalDate.now());

        // 🔥 NE PAS TOUCHER à sectionQuestions, children, etc.
        // Les relations restent intactes

        return repository.save(existingSection);
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

