package com.example.appenquetes1.controller;

import com.example.appenquetes1.dto.Answer.AnswerCreateDTO;
import com.example.appenquetes1.entity.QuestionAnswers;
import com.example.appenquetes1.service.QuestionAnswersService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

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


    @DeleteMapping("/question/{questionId}")
    public void deleteByQuestionId(@PathVariable Integer questionId) {
        System.out.println("🔍 DELETE /questionAnswers/question/" + questionId);
        service.deleteByQuestionId(questionId);
        System.out.println("✅ Réponses supprimées pour la question " + questionId);
    }

    // Ajouter ces méthodes dans QuestionAnswersController.java

    @PutMapping("/{answerId}/add-condi-question/{questionId}")
    public ResponseEntity<?> addConditionalQuestion(
            @PathVariable Integer answerId,
            @PathVariable Integer questionId) {
        try {
            QuestionAnswers updated = service.addConditionalQuestion(answerId, questionId);
            return ResponseEntity.ok(updated);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/{answerId}/add-condi-section/{sectionId}")
    public ResponseEntity<?> addConditionalSection(
            @PathVariable Integer answerId,
            @PathVariable Integer sectionId) {
        try {
            QuestionAnswers updated = service.addConditionalSection(answerId, sectionId);
            return ResponseEntity.ok(updated);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/{answerId}/remove-condi-question/{questionId}")
    public ResponseEntity<?> removeConditionalQuestion(
            @PathVariable Integer answerId,
            @PathVariable Integer questionId) {
        try {
            QuestionAnswers updated = service.removeConditionalQuestion(answerId, questionId);
            return ResponseEntity.ok(updated);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/{answerId}/remove-condi-section/{sectionId}")
    public ResponseEntity<?> removeConditionalSection(
            @PathVariable Integer answerId,
            @PathVariable Integer sectionId) {
        try {
            QuestionAnswers updated = service.removeConditionalSection(answerId, sectionId);
            return ResponseEntity.ok(updated);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }


    @DeleteMapping("/question/{questionId}/full")
    public ResponseEntity<?> deleteFullAnswers(@PathVariable Integer questionId) {
        System.out.println("🗑️ deleteFullAnswers - Question ID: " + questionId);
        try {
            service.deleteFullAnswersByQuestionId(questionId);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/by-question/{questionId}")
    public ResponseEntity<List<QuestionAnswers>> getByQuestionId(@PathVariable Integer questionId) {
        List<QuestionAnswers> answers = service.findByQuestionId(questionId);
        return ResponseEntity.ok(answers);
    }

    @PutMapping("/question/{questionId}/replace-answers")
    public ResponseEntity<?> replaceAnswers(@PathVariable Integer questionId,
                                            @RequestBody List<AnswerCreateDTO> newAnswers) {
        System.out.println("🔄 replace-answers - Question ID: " + questionId);
        System.out.println("📥 Nombre de réponses reçues: " + (newAnswers != null ? newAnswers.size() : 0));
        try {
            service.replaceAnswers(questionId, newAnswers);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}

