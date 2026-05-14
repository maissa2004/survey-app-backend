package com.example.appenquetes1.dto.admin;

import java.util.List;

public class AnswerDetailDTO {
    private Integer id;
    private String codeQuestion;
    private String questionText;
    private String value;
    private String referenceCode;
    private String fileName;
    private String fileType;
    private List<String> selectedOptions; // libelles des nm answers selected

    public List<String> getSelectedOptions() {
        return selectedOptions;
    }

    public void setSelectedOptions(List<String> selectedOptions) {
        this.selectedOptions = selectedOptions;
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getQuestionText() {
        return questionText;
    }

    public void setQuestionText(String questionText) {
        this.questionText = questionText;
    }

    public String getCodeQuestion() {
        return codeQuestion;
    }

    public void setCodeQuestion(String codeQuestion) {
        this.codeQuestion = codeQuestion;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setReferenceCode(String referenceCode) {
        this.referenceCode = referenceCode;
    }

    public String getReferenceCode() {
        return referenceCode;
    }
}