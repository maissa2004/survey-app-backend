package com.example.appenquetes1.service;

import com.example.appenquetes1.entity.NmTypeQuest;
import com.example.appenquetes1.entity.Question;
import com.example.appenquetes1.repository.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QuestionService {

    @Autowired
    private QuestionRepository repository;

    public Question save(Question q) {
        return repository.save(q);
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

}

