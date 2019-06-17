/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.miage.randomembre.metier;

import fr.miage.randomembre.entities.Association;
import fr.miage.randomembre.entities.Membre;
import fr.miage.randomembre.repositories.AssociationInterface;
import java.util.List;
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
        List<Association> listAssos = (List<Association>) associationInterface.findAll();
        if (listAssos.size() != 0){
            return listAssos.get(0);
        }else{
            return null;
        }
        
    }
    
     public Association createAssociation(Association association) {
        List<Association> listAssos = (List<Association>) associationInterface.findAll();
        if (listAssos.size() == 0){
            return this.associationInterface.save(association);
        }else{
            return null;
        }
    }
     
     public void financerRando(float cout) {
         List<Association> listAssos = (List<Association>) associationInterface.findAll();
        if (listAssos.size() != 0){
            Association a = listAssos.get(0);
            a.setBudgetAsso(a.getBudgetAsso() - cout);
            this.associationInterface.save(a);
        }
        
    }

    public String reporting() {
        Optional<Association> assoReturn = this.associationInterface.findById((long) 1);
        if (!assoReturn.isPresent()) {
            //TODO exception 
        }
        Association a = assoReturn.get();
        return "{\"cotisationMin\" : \""+a.getCotisationMin()+"\"}";
    }
     
     
    
}
