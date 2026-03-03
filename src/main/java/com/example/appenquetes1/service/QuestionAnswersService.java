package com.example.appenquetes1.service;

import com.example.appenquetes1.entity.NmAnswers;
import com.example.appenquetes1.entity.QuestionAnswers;
import com.example.appenquetes1.repository.QuestionAnswersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QuestionAnswersService {

    @Autowired
    private QuestionAnswersRepository repository;

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

}

