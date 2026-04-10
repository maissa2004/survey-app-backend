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
        System.out.println("=== RECEIVED QUESTION ===");
        System.out.println("code: " + question.getCode());
        System.out.println("titleFr: " + question.getTitleFr());
        System.out.println("titleEn: " + question.getTitleEn());
        System.out.println("id_nm_type_quest: " + question.getIdNmTypeQuest());
        System.out.println("nmtypeQuest: " + question.getNmtypeQuest());
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

    @PutMapping("/{id}")
    public Question update(@PathVariable Integer id,
                           @RequestBody Question question) {
        System.out.println("=== UPDATE QUESTION ===");
        System.out.println("ID: " + id);
        question.setId(id);
        return service.updateQuestion(id, question);  // ← Utiliser updateQuestion au lieu de save
    }

    // DELETE
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Integer id) {
        service.delete(id);
    }
}

