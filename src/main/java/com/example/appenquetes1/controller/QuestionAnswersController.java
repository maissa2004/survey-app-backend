package com.example.appenquetes1.controller;

import com.example.appenquetes1.entity.QuestionAnswers;
import com.example.appenquetes1.service.QuestionAnswersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/questionAnswers")
@CrossOrigin
public class QuestionAnswersController {

    @Autowired
    private QuestionAnswersService service;

    @PostMapping
    public QuestionAnswers create(@RequestBody QuestionAnswers questionAnswers) {
        return service.save(questionAnswers);
    }

    @GetMapping
    public List<QuestionAnswers> getAll() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public QuestionAnswers getById(@PathVariable Integer id) {
        return service.findById(id);
    }

    // UPDATE
    @PutMapping("/{id}")
    public QuestionAnswers update(@PathVariable Integer id,
                                  @RequestBody QuestionAnswers questionAnswers) {
        questionAnswers.setId(id);
        return service.save(questionAnswers);
    }

    // DELETE
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Integer id) {
        service.delete(id);
    }
}

