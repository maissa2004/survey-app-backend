package com.example.appenquetes1.dto.question;

import com.example.appenquetes1.dto.questionAnswers.QuestionAnswersCreateDTO;

import java.util.List;

public class QuestionCreateDTO {
    private Integer id;
    private Integer idSectionQues;
    private String code;
    private String titleFr;
    private String titleEn;

    private Boolean isRequired;
    private Boolean isConditionnel;

    private String libelleNmtype;
    private String libelleEnNmtype;
    private String codeTypeQues;

    private Boolean hasConditions;

    private List<QuestionAnswersCreateDTO> answers;}
