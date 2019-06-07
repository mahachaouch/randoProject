package fr.miage.randomembre.entities;

import java.io.Serializable;

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
    private Long idA;

    private String nomAsso;
    private Float budgetAsso;
    private Float cotisationMin;

    public Association() {
    }
    
    public Association(String nomAsso, Float budgetAsso, Float cotisationMin) {
        this.cotisationMin = cotisationMin;
        this.nomAsso = nomAsso;
        this.budgetAsso = budgetAsso;
    }

    public Long getIdA() {
        return idA;
    }

    public void setIdA(Long idA) {
        this.idA = idA;
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

    

}
