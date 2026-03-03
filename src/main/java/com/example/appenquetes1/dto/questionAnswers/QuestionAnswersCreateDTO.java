package com.example.appenquetes1.dto.questionAnswers;

import com.example.appenquetes1.dto.question.QuestionCreateDTO;

import java.time.LocalDate;
import java.util.List;

public class QuestionAnswersCreateDTO {
    private Integer id;
    private String code;
    private String libelle;
    private String libelleEn;
    private String reference;
    private LocalDate dtUpdate;

    private List<QuestionCreateDTO> condiQuestion;}
