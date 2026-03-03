package com.example.appenquetes1.controller;

import com.example.appenquetes1.entity.NmAnswers;
import com.example.appenquetes1.service.NmAnswersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/nmAnswers")
@CrossOrigin
public class NmAnswersController {

    @Autowired
    private NmAnswersService service;

    @PostMapping
    public NmAnswers create(@RequestBody NmAnswers nmAnswers) {
        return service.save(nmAnswers);
    }

    @GetMapping
    public List<NmAnswers> getAll() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public NmAnswers getById(@PathVariable Integer id) {
        return service.findById(id);
    }

    // UPDATE
    @PutMapping("/{id}")
    public NmAnswers update(@PathVariable Integer id,
                            @RequestBody NmAnswers nmAnswers) {
        nmAnswers.setid(id);
        return service.save(nmAnswers);
    }

    // DELETE
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Integer id) {
        service.delete(id);
    }
}

