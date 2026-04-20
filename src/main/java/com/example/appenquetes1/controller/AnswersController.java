// com.example.appenquetes1.controller.AnswersController.java
package com.example.appenquetes1.controller;

import com.example.appenquetes1.dto.useranswer.UserAnswerRequestDTO;
import com.example.appenquetes1.dto.useranswer.UserAnswerResponseDTO;
import com.example.appenquetes1.dto.useranswer.UserAnswersSurvey;
import com.example.appenquetes1.service.AnswersService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/answers")
public class AnswersController {

    @Autowired
    private AnswersService answersService;

    @PostMapping
    public ResponseEntity<UserAnswerResponseDTO> create(@RequestBody UserAnswerRequestDTO answer) {
        return ResponseEntity.ok(answersService.save(answer));
    }
    @PostMapping("/submit")
    public ResponseEntity<?> submitAnswers(@RequestBody UserAnswersSurvey answers, HttpServletRequest request) {
        Integer userIdToken = (Integer) request.getAttribute("userId");
        List<UserAnswerRequestDTO> ListAnswers = answers.getResponses();

            if (userIdToken != null) {
                for (UserAnswerRequestDTO answer : ListAnswers) {
                    answer.setIdUser(userIdToken);
                }
            } else {
                Integer userIdFromAnswers = answers.getIdUser();
                if (userIdFromAnswers != null) {
                    for (UserAnswerRequestDTO answerA : ListAnswers) {
                        if (answerA.getIdUser() == null) {
                            answerA.setIdUser(userIdFromAnswers);
                        }
                    }
                }
            }

            Integer surveyId = answers.getIdSurvey();
            if (surveyId != null) {
                for (UserAnswerRequestDTO answerA : ListAnswers) {
                    if (answerA.getIdSurvey() == null) {
                        answerA.setIdSurvey(surveyId);
                    }
                }
            }
        System.out.println("Received answers: " + ListAnswers);
        answersService.submitSurveyAnswers(ListAnswers);

        return ResponseEntity.ok("Survey submitted successfully");
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