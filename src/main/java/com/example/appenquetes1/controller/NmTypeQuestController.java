package com.example.appenquetes1.controller;

import com.example.appenquetes1.entity.NmTypeQuest;
import com.example.appenquetes1.service.NmTypeQuestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/nmTypeQuest")
//@CrossOrigin(origins = "http://localhost:4200")
public class NmTypeQuestController {

    @Autowired
    private NmTypeQuestService service;

    @PostMapping
    public NmTypeQuest create(@RequestBody NmTypeQuest nmTypeQuest) {
        return service.save(nmTypeQuest);
    }

    @GetMapping
    public List<NmTypeQuest> getAll() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public NmTypeQuest getById(@PathVariable Integer id) {
        return service.findById(id);
    }

    // UPDATE
    @PutMapping("/{id}")
    public NmTypeQuest update(@PathVariable Integer id,
                              @RequestBody NmTypeQuest nmTypeQuest) {
        nmTypeQuest.setId(id);
        return service.save(nmTypeQuest);
    }

    // DELETE
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Integer id) {
        service.delete(id);
    }
}

