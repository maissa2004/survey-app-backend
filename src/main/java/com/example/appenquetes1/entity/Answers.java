package com.example.appenquetes1.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "answers")
public class Answers {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "id_section_quest")
    private Integer idSectionQuest;

    @Column(name = "id_user")
    private Integer idUser;

    @Column(columnDefinition = "LONGTEXT")
    private String value;

    @Column(name = "file_name", nullable = false)
    private String fileName = "";

    @Column(name = "reference_code", nullable = false)
    private String referenceCode = "";

    @Column(name = "file_type", nullable = false, length = 50)
    private String fileType = "";

    @Column(name = "file_size", nullable = false)
    private Integer fileSize = 0;

    @ManyToMany
    @JoinTable(
            name = "answer_nm_answer",
            joinColumns = @JoinColumn(name = "answer_id"),
            inverseJoinColumns = @JoinColumn(name = "nm_answer_id")
    )
    private List<NmAnswers> nmAnswers;

    @Column(name = "dt_update")
    private LocalDateTime dtUpdate;

    @Column(name = "code_question")
    private String codeQuestion;

    @Column(name = "id_survey")
    private Integer idSurvey;

    // Relations
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_user", insertable = false, updatable = false)
    private User user;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_survey", insertable = false, updatable = false)
    private Survey survey;

    @PrePersist
    protected void onCreate() {
        dtUpdate = LocalDateTime.now();
        if (fileName == null) fileName = "";
        if (referenceCode == null) referenceCode = "";
        if (fileType == null) fileType = "";
        if (fileSize == null) fileSize = 0;
    }

    @PreUpdate
    protected void onUpdate() {
        dtUpdate = LocalDateTime.now();
    }

    public Answers() {}

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


    public List<NmAnswers> getNmAnswers() {
        return nmAnswers;
    }

    public void setNmAnswers(List<NmAnswers> nmAnswers) {
        this.nmAnswers = nmAnswers;
    }

    public LocalDateTime getDtUpdate() { return dtUpdate; }
    public void setDtUpdate(LocalDateTime dtUpdate) { this.dtUpdate = dtUpdate; }

    public String getCodeQuestion() { return codeQuestion; }
    public void setCodeQuestion(String codeQuestion) { this.codeQuestion = codeQuestion; }

    public Integer getIdSurvey() { return idSurvey; }
    public void setIdSurvey(Integer idSurvey) { this.idSurvey = idSurvey; }

    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }


    public Survey getSurvey() { return survey; }
    public void setSurvey(Survey survey) { this.survey = survey; }
}