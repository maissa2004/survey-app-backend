package com.example.appenquetes1.controller;

import com.example.appenquetes1.dto.section.SectionResponseDTO;
import com.example.appenquetes1.entity.Section;
import com.example.appenquetes1.mapper.SectionMapper;
import com.example.appenquetes1.service.SectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/section")
@CrossOrigin
public class SectionController {

    @Autowired
    private SectionService service;

    @PostMapping
    public Section create(@RequestBody Section section) {
        System.out.println("=== RECEIVED SECTION ===");
        System.out.println("code: " + section.getCode());
        System.out.println("title: " + section.getTitle());
        System.out.println("conditionnel: " + section.isConditionnel());
        System.out.println("ordre: " + section.getOrdre());
        System.out.println("idSurvey: " + section.getSurvey());
        return service.save(section);
    }

    @GetMapping
    public List<Section> getAll() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public Section getById(@PathVariable Integer id) {
        return service.findById(id);
    }

    // UPDATE
    @PutMapping("/{id}")
    public Section update(@PathVariable Integer id,
                          @RequestBody Section section) {
        section.setId(id);
        return service.save(section);
    }

    // DELETE
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Integer id) {
        service.delete(id);
    }

    @GetMapping("/detailFullSection/{id}")
    public SectionResponseDTO getFullSection(@PathVariable Integer id) {
          Section section = service.getFullSection(id);
          return SectionMapper.toDTO(section);
    }


}

