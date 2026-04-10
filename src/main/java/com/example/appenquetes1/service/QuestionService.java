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

    public Question save(Question question) {
        System.out.println("=== SAVING QUESTION ===");
        System.out.println("id_nm_type_quest: " + question.getIdNmTypeQuest());
        System.out.println("nmtypeQuest: " + question.getNmtypeQuest());
        return repository.save(question);
    }

    public List<Question> findAll() {
        return repository.findAll();
    }

    public Question findById(Integer id) {
        return repository.findById(id).orElse(null);
    }

    public void delete(Integer id) {
        repository.deleteById(id);
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

        // 1. Récupérer la question existante
        Question existingQuestion = repository.findById(id).orElse(null);
        if (existingQuestion == null) {
            return null;
        }

        // 2. Mettre à jour les champs simples
        existingQuestion.setCode(questionData.getCode());
        existingQuestion.setTitleFr(questionData.getTitleFr());
        existingQuestion.setTitleEn(questionData.getTitleEn());
        existingQuestion.setNmtypeQuest(questionData.getNmtypeQuest());

        // 3. Supprimer les anciennes réponses
        System.out.println("🔍 Suppression des anciennes réponses...");
        existingQuestion.getAnswers().clear();

        // 4. Ajouter les nouvelles réponses
        if (questionData.getAnswers() != null) {
            for (QuestionAnswers newAnswer : questionData.getAnswers()) {
                newAnswer.setQuestion(existingQuestion);
                existingQuestion.getAnswers().add(newAnswer);
            }
        }

        // 5. Sauvegarder
        Question saved = repository.save(existingQuestion);
        System.out.println("✅ Question mise à jour avec " + saved.getAnswers().size() + " réponse(s)");
        return saved;
    }


}

