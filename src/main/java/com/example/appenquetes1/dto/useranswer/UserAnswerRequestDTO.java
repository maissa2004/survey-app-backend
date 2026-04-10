// com.example.appenquetes1.dto.useranswer.UserAnswerRequestDTO.java
package com.example.appenquetes1.dto.useranswer;

public class UserAnswerRequestDTO {
    private Integer idSectionQuest;
    private Integer idUser;
    private String value;
    private String fileName;
    private String referenceCode;
    private String fileType;
    private Integer fileSize;
    private Integer idNmAnswer;
    private String codeQuestion;
    private Integer idSurvey;

    // Getters et Setters
    public Integer getIdSectionQuest() { return idSectionQuest; }
    public void setIdSectionQuest(Integer idSectionQuest) { this.idSectionQuest = idSectionQuest; }

    public Integer getIdUser() { return idUser; }
    public void setIdUser(Integer idUser) { this.idUser = idUser; }

    public String getValue() { return value; }
    public void setValue(String value) { this.value = value; }

    public String getFileName() { return fileName; }
    public void setFileName(String fileName) { this.fileName = fileName; }

    public String getReferenceCode() { return referenceCode; }
    public void setReferenceCode(String referenceCode) { this.referenceCode = referenceCode; }

    public String getFileType() { return fileType; }
    public void setFileType(String fileType) { this.fileType = fileType; }

    public Integer getFileSize() { return fileSize; }
    public void setFileSize(Integer fileSize) { this.fileSize = fileSize; }

    public Integer getIdNmAnswer() { return idNmAnswer; }
    public void setIdNmAnswer(Integer idNmAnswer) { this.idNmAnswer = idNmAnswer; }

    public String getCodeQuestion() { return codeQuestion; }
    public void setCodeQuestion(String codeQuestion) { this.codeQuestion = codeQuestion; }

    public Integer getIdSurvey() { return idSurvey; }
    public void setIdSurvey(Integer idSurvey) { this.idSurvey = idSurvey; }
}