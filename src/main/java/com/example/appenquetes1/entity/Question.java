package com.example.appenquetes1.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "question")
public class Question {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String code;
    private String titleFr;
    private String titleEn;
    private String reference;        // ← NOUVEAU
    private String descriptionFr;    // ← NOUVEAU
    private String description;

    @OneToMany(mappedBy = "question", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private Set<QuestionAnswers> answers = new HashSet<>();


    @ManyToOne
    @JoinColumn(name = "parent_answer_id")
    @JsonIgnore
    private QuestionAnswers parentAnswer;

    @ManyToOne
    @JoinColumn(name = "id_nm_type_quest")
    private NmTypeQuest nmtypeQuest;



    // Getter et Setter pour id_nm_type_quest
    public Integer getIdNmTypeQuest() {
        return nmtypeQuest != null ? nmtypeQuest.getId() : null;
    }

    // Ajoute cette méthode
    @JsonProperty("id_nm_type_quest")
    public void setIdNmTypeQuest(Integer idNmTypeQuest) {
        if (idNmTypeQuest != null) {
            this.nmtypeQuest = new NmTypeQuest();
            this.nmtypeQuest.setId(idNmTypeQuest);
        }
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getTitleFr() {
        return titleFr;
    }

    public void setTitleFr(String titleFr) {
        this.titleFr = titleFr;
    }

    public String getTitleEn() {
        return titleEn;
    }

    public void setTitleEn(String titleEn) {
        this.titleEn = titleEn;
    }
    public Set<QuestionAnswers> getAnswers() {
        return answers;
    }

    public void setAnswers(Set<QuestionAnswers> answers) {
        this.answers = answers;
    }

    public QuestionAnswers getParentAnswer() {
        return parentAnswer;
    }

    public void setParentAnswer(QuestionAnswers parentAnswer) {
        this.parentAnswer = parentAnswer;
    }

    public NmTypeQuest getNmtypeQuest() {
        return nmtypeQuest;
    }

    public void setNmtypeQuest(NmTypeQuest nmtypeQuest) {
        this.nmtypeQuest = nmtypeQuest;
    }


}