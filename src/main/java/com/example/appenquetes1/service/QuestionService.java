package com.example.appenquetes1.service;

import com.example.appenquetes1.entity.NmTypeQuest;
import com.example.appenquetes1.entity.Question;
import com.example.appenquetes1.entity.QuestionAnswers;
import com.example.appenquetes1.repository.QuestionRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QuestionService {

    @Autowired
    private QuestionRepository repository;

    @Autowired
    private QuestionAnswersService questionAnswersService;

    @Autowired
    private SectionQuestionService sectionQuestionService;


    public Question save(Question question) {
        System.out.println("=== SAVING QUESTION ===");
        System.out.println("code: " + question.getCode());
        System.out.println("titleFr: " + question.getTitleFr());
        System.out.println("titleEn: " + question.getTitleEn());
        System.out.println("id_nm_type_quest: " + question.getIdNmTypeQuest());
        System.out.println("nmtypeQuest: " + question.getNmtypeQuest());
        System.out.println("answers: " + (question.getAnswers() != null ? question.getAnswers().size() : 0));
        return repository.save(question);

    }

    public List<Question> findAll() {
        return repository.findAll();
    }

    public Question findById(Integer id) {
        return repository.findById(id).orElse(null);
    }

    @Transactional
    public void delete(Integer id) {
        System.out.println("🔍 Suppression de la question ID: " + id);

        // 1. 🔥 D'abord, récupérer la question
        Question question = repository.findById(id).orElse(null);
        if (question == null) {
            System.out.println("❌ Question non trouvée");
            return;
        }

        // 2. 🔥 Dissocier cette question si elle est utilisée comme condition parent
        if (question.getParentAnswer() != null) {
            question.setParentAnswer(null);
            repository.save(question);
        }

        // 3. 🔥 Chercher et dissocier les questions qui référencent cette question comme parent
        List<Question> children = repository.findByParentAnswerQuestionId(id);
        for (Question child : children) {
            child.setParentAnswer(null);
            repository.save(child);
        }

        // 4. 🔥 Supprimer les liens section_question
        sectionQuestionService.deleteByQuestionId(id);

        // 5. 🔥 SUPPRIMER LES RÉPONSES - Utiliser la méthode full qui gère tout
        // NE PAS appeler deleteByQuestionId séparément !
        questionAnswersService.deleteFullAnswersByQuestionId(id);

        // 6. 🔥 Enfin supprimer la question
        repository.deleteById(id);

        System.out.println("✅ Question supprimée avec succès");
    }

    public List<Question> findByTitleFr(String titleFr) {
        return repository.findByTitleFr(titleFr);
    }

    /*public NmTypeQuest findTypeByQuestionId(Integer id) {

        Question question = findById(id);

        return question.getNmTypeQuest();
    }*/

    @Transactional
    public Question updateQuestion(Integer id, Question questionData) {
        System.out.println("=== UPDATING QUESTION ID: " + id + " ===");

        Question existingQuestion = repository.findById(id).orElse(null);
        if (existingQuestion == null) {
            return null;
        }

        // Mettre à jour uniquement les champs simples
        existingQuestion.setCode(questionData.getCode());
        existingQuestion.setTitleFr(questionData.getTitleFr());
        existingQuestion.setTitleEn(questionData.getTitleEn());
        existingQuestion.setNmtypeQuest(questionData.getNmtypeQuest());

        // 🔥 SUPPRIMEZ CES LIGNES - Ne pas toucher aux réponses ici !
        // existingQuestion.getAnswers().clear();
        // if (questionData.getAnswers() != null) { ... }

        Question saved = repository.save(existingQuestion);
        System.out.println("✅ Question mise à jour (ID: " + saved.getId() + ")");
        return saved;
    }


}