// com.example.appenquetes1.dto.useranswer.UserAnswerResponseDTO.java
package com.example.appenquetes1.dto.useranswer;

import java.time.LocalDateTime;
import java.util.List;

public class UserAnswerResponseDTO {
    private Integer id;
    private Integer idSectionQuest;
    private Integer idUser;
    private String value;
    private String fileName;
    private String referenceCode;
    private String fileType;
    private Integer fileSize;
    private List<Integer> idNmAnswer;
    private LocalDateTime dtUpdate;
    private String codeQuestion;
    private Integer idSurvey;
    private Integer submissionId;

    // Données liées
    private String username;
    private String nmAnswerLibelle;
    private String nmAnswerCode;
    private String surveyCode;
    private String surveyLibelle;

    // Getters et Setters
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

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

    public List<Integer> getIdNmAnswer() { return idNmAnswer; }
    public void setIdNmAnswer(List<Integer> idNmAnswer) { this.idNmAnswer = idNmAnswer; }

    public LocalDateTime getDtUpdate() { return dtUpdate; }
    public void setDtUpdate(LocalDateTime dtUpdate) { this.dtUpdate = dtUpdate; }

    public String getCodeQuestion() { return codeQuestion; }
    public void setCodeQuestion(String codeQuestion) { this.codeQuestion = codeQuestion; }

    public Integer getIdSurvey() { return idSurvey; }
    public void setIdSurvey(Integer idSurvey) { this.idSurvey = idSurvey; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getNmAnswerLibelle() { return nmAnswerLibelle; }
    public void setNmAnswerLibelle(String nmAnswerLibelle) { this.nmAnswerLibelle = nmAnswerLibelle; }

    public String getNmAnswerCode() { return nmAnswerCode; }
    public void setNmAnswerCode(String nmAnswerCode) { this.nmAnswerCode = nmAnswerCode; }

    public String getSurveyCode() { return surveyCode; }
    public void setSurveyCode(String surveyCode) { this.surveyCode = surveyCode; }

    public String getSurveyLibelle() { return surveyLibelle; }
    public void setSurveyLibelle(String surveyLibelle) { this.surveyLibelle = surveyLibelle; }

    public void setSubmissionId(Integer submissionId) { this.submissionId = submissionId; }

}