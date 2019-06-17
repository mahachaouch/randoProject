/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.miage.randomembre.metier;

import fr.miage.randomembre.entities.Association;
import fr.miage.randomembre.entities.Membre;
import fr.miage.randomembre.repositories.AssociationInterface;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

/**
 *
 * @author Maha
 */
@Controller
public class GestionAssociation {
    
    @Autowired
    private AssociationInterface associationInterface;

    public Association getAssociation() {
        Optional<Association> assoReturn = this.associationInterface.findById((long) 1);
        if (!assoReturn.isPresent()) {
            //TODO exception 
        }
        return assoReturn.get();
    }
    
     public Association createAssociation(Association association) {
        return this.associationInterface.save(association);
    }
     
     public void financerRando(float cout) {
        Optional<Association> assoReturn = this.associationInterface.findById((long) 1);
        if (!assoReturn.isPresent()) {
            //TODO exception 
        }
        Association a = assoReturn.get();
        a.setBudgetAsso(a.getBudgetAsso() - cout);
        this.associationInterface.save(a);
    }
    
}
