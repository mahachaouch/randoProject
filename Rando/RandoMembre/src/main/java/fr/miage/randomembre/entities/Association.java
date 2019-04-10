package fr.miage.randomembre.entities;

import java.io.Serializable;
import java.util.ArrayList;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.springframework.data.annotation.Id;

@Entity
@Table(name = "association")
public class Association {
	
	    @Id
	    private Long idR;
	    
	    private String nomAsso;
	    private Float budgetAsso;
	    private Float cotisationMin;
	    
	    @OneToMany
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
		
		public void setBudgetAsso(Float budgetAsso) {
			this.budgetAsso = budgetAsso;
		}
		
		public Association(String nomAsso, Float budgetAsso,Float cotisationMin) {
			this.cotisationMin = cotisationMin;
			this.nomAsso = nomAsso;
			this.budgetAsso = budgetAsso;
		}
	    
	    

}