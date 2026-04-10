package com.example.appenquetes1.controller;

import com.example.appenquetes1.entity.SectionQuestion;
import com.example.appenquetes1.service.SectionQuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/sectionQuestion")
@CrossOrigin
public class SectionQuestionController {

    @Autowired
    private SectionQuestionService service;

    @PostMapping
    public SectionQuestion create(@RequestBody SectionQuestion sectionQuestion) {
        System.out.println("=== RECEIVED SECTION QUESTION ===");
        System.out.println("section id: " + (sectionQuestion.getSection() != null ? sectionQuestion.getSection().getId() : "null"));
        System.out.println("question id: " + (sectionQuestion.getQuestion() != null ? sectionQuestion.getQuestion().getId() : "null"));
        return service.save(sectionQuestion);
    }

    @GetMapping
    public List<SectionQuestion> getAll() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public SectionQuestion getById(@PathVariable Integer id) {
        return service.findById(id);
    }

    // UPDATE
    @PutMapping("/{id}")
    public SectionQuestion update(@PathVariable Integer id,
                                  @RequestBody SectionQuestion sectionQuestion) {
        sectionQuestion.setId(id);
        return service.save(sectionQuestion);
    }

    // DELETE
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Integer id) {
        service.delete(id);
    } 
}