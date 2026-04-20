// com.example.appenquetes1.controller.AnswersController.java
package com.example.appenquetes1.controller;

import com.example.appenquetes1.dto.useranswer.UserAnswerRequestDTO;
import com.example.appenquetes1.dto.useranswer.UserAnswerResponseDTO;
import com.example.appenquetes1.service.AnswersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/answers")
//@CrossOrigin(origins = "http://localhost:4200")
public class AnswersController {

    @Autowired
    private AnswersService answersService;

    @PostMapping
    public ResponseEntity<UserAnswerResponseDTO> create(@RequestBody UserAnswerRequestDTO answer) {
        return ResponseEntity.ok(answersService.save(answer));
    }

    @PostMapping("/batch")
    public ResponseEntity<List<UserAnswerResponseDTO>> createBatch(@RequestBody List<UserAnswerRequestDTO> answers) {
        return ResponseEntity.ok(answersService.saveAll(answers));
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<UserAnswerResponseDTO>> getByUser(@PathVariable Integer userId) {
        return ResponseEntity.ok(answersService.findByUser(userId));
    }

    @GetMapping("/survey/{surveyId}")
    public ResponseEntity<List<UserAnswerResponseDTO>> getBySurvey(@PathVariable Integer surveyId) {
        return ResponseEntity.ok(answersService.findBySurvey(surveyId));
    }

    @GetMapping("/user/{userId}/survey/{surveyId}")
    public ResponseEntity<List<UserAnswerResponseDTO>> getByUserAndSurvey(@PathVariable Integer userId,
                                                                          @PathVariable Integer surveyId) {
        return ResponseEntity.ok(answersService.findByUserAndSurvey(userId, surveyId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserAnswerResponseDTO> getById(@PathVariable Integer id) {
        UserAnswerResponseDTO answer = answersService.findById(id);
        return answer != null ? ResponseEntity.ok(answer) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        answersService.delete(id);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/user/{userId}/survey/{surveyId}")
    public ResponseEntity<Void> deleteByUserAndSurvey(@PathVariable Integer userId,
                                                      @PathVariable Integer surveyId) {
        answersService.deleteByUserAndSurvey(userId, surveyId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/user/{userId}/survey/{surveyId}/count")
    public ResponseEntity<Long> countUserAnswers(@PathVariable Integer userId,
                                                 @PathVariable Integer surveyId) {
        return ResponseEntity.ok(answersService.countUserAnswers(userId, surveyId));
    }
}