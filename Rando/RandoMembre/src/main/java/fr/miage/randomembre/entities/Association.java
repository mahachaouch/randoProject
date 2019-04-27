package fr.miage.randomembre.entities;

import java.io.Serializable;
import java.util.ArrayList;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Table;

import org.springframework.data.annotation.Id;

@Entity
@Table(name = "association")
public class Association implements Serializable {

    @Id
    @GeneratedValue
    @javax.persistence.Id
    private Long idR;

    private String nomAsso;
    private Float budgetAsso;
    private Float cotisationMin;

    public Association() {
    }

    private ArrayList<Membre> membresAsso;

    public Long getIdR() {
        return idR;
    }

    public void setIdR(Long idR) {
        this.idR = idR;
    }

    public String getNomAsso() {
        return nomAsso;
    }

    public void setNomAsso(String nomAsso) {
        this.nomAsso = nomAsso;
    }

    public Float getBudgetAsso() {
        return budgetAsso;
    }

    public Float getCotisationMin() {
        return this.cotisationMin;
    }

    public void setBudgetAsso(Float budgetAsso) {
        this.budgetAsso = budgetAsso;
    }

    public Association(String nomAsso, Float budgetAsso, Float cotisationMin) {
        this.cotisationMin = cotisationMin;
        this.nomAsso = nomAsso;
        this.budgetAsso = budgetAsso;
    }

}
