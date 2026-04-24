package com.example.appenquetes1.service;

import com.example.appenquetes1.entity.QuestionAnswers;
import com.example.appenquetes1.entity.SectionQuestion;
import com.example.appenquetes1.repository.SectionQuestionRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SectionQuestionService {

    @Autowired
    private SectionQuestionRepository repository;

    public SectionQuestionService(SectionQuestionRepository repository) {
        this.repository = repository;
    }


    public SectionQuestion save(SectionQuestion sq) {
        return repository.save(sq);
    }

    public List<SectionQuestion> findAll() {
        return repository.findAll();
    }

    public SectionQuestion findById(Integer id) {
        return repository.findById(id).orElse(null);
    }

    public void delete(Integer id) {
        repository.deleteById(id);
    }

    public List<SectionQuestion> findByOrdre(Integer ordre) {
        return repository.findByOrdre(ordre);
    }

    @Transactional
    public void deleteByQuestionId(Integer questionId) {
        repository.deleteByQuestionId(questionId);
        System.out.println("✅ SectionQuestion supprimées pour la question " + questionId);
    }

    /*public List<QuestionAnswers> findAnswersBySectionQuestionId(Integer id) {

        SectionQuestion sq = findById(id);

        return sq.getQuestionAnswers();
    }*/

}

