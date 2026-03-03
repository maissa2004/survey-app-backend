package com.example.appenquetes1.controller;

import com.example.appenquetes1.entity.Question;
import com.example.appenquetes1.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/question")
@CrossOrigin
public class QuestionController {

    @Autowired
    private QuestionService service;

    @PostMapping
    public Question create(@RequestBody Question question) {
        return service.save(question);
    }

    @GetMapping
    public List<Question> getAll() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public Question getById(@PathVariable Integer id) {
        return service.findById(id);
    }

    // UPDATE
    @PutMapping("/{id}")
    public Question update(@PathVariable Integer id,
                           @RequestBody Question question) {
        question.setId(id);
        return service.save(question);
    }

    // DELETE
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Integer id) {
        service.delete(id);
    }
}

