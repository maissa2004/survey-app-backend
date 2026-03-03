package com.example.appenquetes1.controller;

import com.example.appenquetes1.entity.EtatSurvey;
import com.example.appenquetes1.service.EtatSurveyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/etatSurvey")
@CrossOrigin
public class EtatSurveyController {

    @Autowired
    private EtatSurveyService service;

    @PostMapping
    public EtatSurvey create(@RequestBody EtatSurvey etatSurvey) {
        return service.save(etatSurvey);
    }

    @GetMapping
    public List<EtatSurvey> getAll() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public EtatSurvey getById(@PathVariable Integer id) {
        return service.findById(id);
    }

    // UPDATE
    @PutMapping("/{id}")
    public EtatSurvey update(@PathVariable Integer id,
                             @RequestBody EtatSurvey etatSurvey) {
        etatSurvey.setid(id);
        return service.save(etatSurvey);
    }

    // DELETE
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Integer id) {
        service.delete(id);
    }
}

