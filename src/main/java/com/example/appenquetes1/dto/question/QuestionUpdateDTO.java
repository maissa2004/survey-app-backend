package com.example.appenquetes1.dto.question;

import com.fasterxml.jackson.annotation.JsonProperty;

public class QuestionUpdateDTO {
    private String code;
    private String titleFr;
    private String titleEn;
    @JsonProperty("id_nm_type_quest")
    private Integer idNmTypeQuest;

    // Constructeurs
    public QuestionUpdateDTO() {}

    public QuestionUpdateDTO(String code, String titleFr, String titleEn, Integer idNmTypeQuest) {
        this.code = code;
        this.titleFr = titleFr;
        this.titleEn = titleEn;
        this.idNmTypeQuest = idNmTypeQuest;
    }

    // Getters et Setters
    public String getCode() { return code; }
    public void setCode(String code) { this.code = code; }

    public String getTitleFr() { return titleFr; }
    public void setTitleFr(String titleFr) { this.titleFr = titleFr; }

    public String getTitleEn() { return titleEn; }
    public void setTitleEn(String titleEn) { this.titleEn = titleEn; }

    public Integer getIdNmTypeQuest() { return idNmTypeQuest; }
    public void setIdNmTypeQuest(Integer idNmTypeQuest) { this.idNmTypeQuest = idNmTypeQuest; }
}