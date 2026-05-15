package com.example.appenquetes1.controller;

import com.example.appenquetes1.dto.question.QuestionUpdateDTO;
import com.example.appenquetes1.entity.NmTypeQuest;
import com.example.appenquetes1.entity.Question;
import com.example.appenquetes1.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

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
    public ResponseEntity<?> update(@PathVariable Integer id,
                                    @RequestBody Map<String, Object> payload) {
        System.out.println("=== UPDATE QUESTION ===");
        System.out.println("ID: " + id);
        System.out.println("Payload reçu: " + payload);

        // 🔥 Lire la valeur avec la clé snake_case
        Integer idNmTypeQuest = null;
        if (payload.containsKey("id_nm_type_quest")) {
            idNmTypeQuest = (Integer) payload.get("id_nm_type_quest");
        } else if (payload.containsKey("idNmTypeQuest")) {
            idNmTypeQuest = (Integer) payload.get("idNmTypeQuest");
        }

        String code = (String) payload.get("code");
        String titleFr = (String) payload.get("titleFr");
        String titleEn = (String) payload.get("titleEn");

        System.out.println("Code reçu: " + code);
        System.out.println("TitleFr reçu: " + titleFr);
        System.out.println("idNmTypeQuest reçu: " + idNmTypeQuest);

        Question questionToUpdate = new Question();
        questionToUpdate.setCode(code);
        questionToUpdate.setTitleFr(titleFr);
        questionToUpdate.setTitleEn(titleEn != null ? titleEn : "");

        if (idNmTypeQuest != null) {
            NmTypeQuest type = new NmTypeQuest();
            type.setId(idNmTypeQuest);
            questionToUpdate.setNmtypeQuest(type);
        }

        Question updated = service.updateQuestion(id, questionToUpdate);
        if (updated == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Integer id) {
        service.delete(id);
    }
}