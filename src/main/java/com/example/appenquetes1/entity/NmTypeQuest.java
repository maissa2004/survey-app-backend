package com.example.appenquetes1.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "nm_type_quest")
public class NmTypeQuest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonProperty("id")  // ← Force l'inclusion dans le JSON
    private Integer id;
    private String code;
    private String libelle;
    private String libelleEn;
    private int coefficient;
    private LocalDate dtUpdate;

    @OneToMany(mappedBy = "nmtypeQuest")
    private List<Question> questions;

    public NmTypeQuest() {
    }

    public NmTypeQuest(Integer id, String code, String libelleEn, String libelle, int coefficient, LocalDate dtUpdate) {
        this.id = id;
        this.code = code;
        this.libelleEn = libelleEn;
        this.libelle = libelle;
        this.coefficient = coefficient;
        this.dtUpdate = dtUpdate;
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

    public String getLibelle() {
        return libelle;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    public String getLibelleEn() {
        return libelleEn;
    }

    public void setLibelleEn(String libelleEn) {
        this.libelleEn = libelleEn;
    }

    public int getCoefficient() {
        return coefficient;
    }

    public void setCoefficient(int coefficient) {
        this.coefficient = coefficient;
    }

    public LocalDate getDtUpdate() {
        return dtUpdate;
    }

    public void setDtUpdate(LocalDate dtUpdate) {
        this.dtUpdate = dtUpdate;
    }
}
