package com.example.appenquetes1.dto.question;

import com.example.appenquetes1.dto.Answer.AnswerResponseDTO;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

public class QuestionResponseDTO {

    private Integer id;
    private Integer idSectionQues;

    private String code;
    private String titleFr;
    private String titleEn;
    @JsonProperty("id_nm_type_quest")
    private Integer idNmTypeQuest;

    private boolean isRequired;
    private boolean isConditionnel;

    private String codeTypeQues;
    private String libelleNmtype;
    private String libelleEnNmtype;

    private boolean hasConditions;

    // 🔥 INITIALISER AVEC UNE LISTE VIDE
    private List<AnswerResponseDTO> answers = new ArrayList<>();

    public QuestionResponseDTO() {}

    // Getters et Setters...
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public Integer getIdSectionQues() { return idSectionQues; }
    public void setIdSectionQues(Integer idSectionQues) { this.idSectionQues = idSectionQues; }

    public String getCode() { return code; }
    public void setCode(String code) { this.code = code; }

    public String getTitleFr() { return titleFr; }
    public void setTitleFr(String titleFr) { this.titleFr = titleFr; }

    public String getTitleEn() { return titleEn; }
    public void setTitleEn(String titleEn) { this.titleEn = titleEn; }

    public Integer getIdNmTypeQuest() { return idNmTypeQuest; }
    public void setIdNmTypeQuest(Integer idNmTypeQuest) { this.idNmTypeQuest = idNmTypeQuest; }

    public boolean isRequired() { return isRequired; }
    public void setRequired(boolean required) { isRequired = required; }

    public boolean isConditionnel() { return isConditionnel; }
    public void setConditionnel(boolean conditionnel) { isConditionnel = conditionnel; }

    public String getCodeTypeQues() { return codeTypeQues; }
    public void setCodeTypeQues(String codeTypeQues) { this.codeTypeQues = codeTypeQues; }

    public String getLibelleNmtype() { return libelleNmtype; }
    public void setLibelleNmtype(String libelleNmtype) { this.libelleNmtype = libelleNmtype; }

    public String getLibelleEnNmtype() { return libelleEnNmtype; }
    public void setLibelleEnNmtype(String libelleEnNmtype) { this.libelleEnNmtype = libelleEnNmtype; }

    public boolean isHasConditions() { return hasConditions; }
    public void setHasConditions(boolean hasConditions) { this.hasConditions = hasConditions; }

    public List<AnswerResponseDTO> getAnswers() { return answers; }
    public void setAnswers(List<AnswerResponseDTO> answers) {
        this.answers = answers != null ? answers : new ArrayList<>();
    }
}