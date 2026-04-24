package com.example.appenquetes1.service;

import com.example.appenquetes1.entity.NmAnswers;
import com.example.appenquetes1.entity.QuestionAnswers;
import com.example.appenquetes1.repository.NmAnswersRepository;
import com.example.appenquetes1.repository.QuestionAnswersRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QuestionAnswersService {

    @Autowired
    private QuestionAnswersRepository repository;

    @Autowired
    private NmAnswersRepository nmAnswersRepository;

    public QuestionAnswers save(QuestionAnswers qa) {
        return repository.save(qa);
    }

    public List<QuestionAnswers> findAll() {
        return repository.findAll();
    }

    public QuestionAnswers findById(Integer id) {
        return repository.findById(id).orElse(null);
    }

    public void delete(Integer id) {
        repository.deleteById(id);
    }

    public List<QuestionAnswers> findByIsConditionnel(Boolean conditionnel) {
        return repository.findByIsConditionnel(conditionnel);
    }

    /*public List<NmAnswers> findNmAnswersByQuestionAnswersId(Integer id) {

        QuestionAnswers qa = findById(id);

        return qa.getNmAnswers();
    }*/
    public List<QuestionAnswers> findByQuestionId(Integer questionId) {
        return repository.findByQuestionId(questionId);
    }

    @Transactional
    public void deleteByQuestionId(Integer questionId) {
        System.out.println("🔍 DELETE BY QUESTION ID: " + questionId);

        // 1. Récupérer les IDs des NmAnswers associées (sans charger les entités complètes)
        List<Integer> nmAnswerIds = repository.findNmAnswerIdsByQuestionId(questionId);
        System.out.println("🔍 Nombre de NmAnswers associées: " + nmAnswerIds.size());

        // 2. Supprimer d'abord les QuestionAnswers
        int deleted = repository.deleteByQuestionId(questionId);
        System.out.println("✅ " + deleted + " QuestionAnswers supprimées");

        // 3. MAINTENANT supprimer les NmAnswers (après que les QuestionAnswers sont parties)
        for (Integer nmId : nmAnswerIds) {
            System.out.println("🔍 Suppression NmAnswers ID: " + nmId);
            nmAnswersRepository.deleteById(nmId);
        }
        System.out.println("✅ " + nmAnswerIds.size() + " NmAnswers supprimées");
    }
}

