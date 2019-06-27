/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.miage.randomembre.metier;

import fr.miage.randomembre.entities.Association;
import fr.miage.randomembre.entities.Membre;
import fr.miage.randomembre.repositories.AssociationInterface;
import java.security.InvalidParameterException;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class GestionAssociation {
    
    @Autowired
    private AssociationInterface associationInterface;

    //retourne l'association stocké dans le repo
    /**
     * retourne l'association contenu dans le repo
     * @return 
     */
    public Association getAssociation() {
        List<Association> listAssos = (List<Association>) associationInterface.findAll();
        if (listAssos.size() != 0){
            return listAssos.get(0);
        }else{
            return null;
        }
        
    }
    
    /**
     * creer l'association dans le repo si celui-ci est vide et retourne l'association créé
     * @param association
     * @return 
     */
     public Association createAssociation(Association association) {
        List<Association> listAssos = (List<Association>) associationInterface.findAll();
        if (listAssos.size() == 0){
            
            if (association.getBudgetAsso()<0 || association.getCotisationMin()<0 || association.getNomAsso() == ""){
                    throw new InvalidParameterException("asso param >= 0 et nom dif de rien");
            }
            
            
            return this.associationInterface.save(association);
        }else{
            throw new InvalidParameterException("asso déjà créé");
        }
    }
     
     /**
      * retire budget de l'association de cout passé en parametre
      * @param cout 
      */
     public void financerRando(float cout) {
        if (cout < 0){
                throw new InvalidParameterException("cout rando doit etre > 0 ");
        }
         List<Association> listAssos = (List<Association>) associationInterface.findAll();
        if (listAssos.size() != 0){
            Association a = listAssos.get(0);
            a.setBudgetAsso(a.getBudgetAsso() - cout);
            this.associationInterface.save(a);
        }
        
    }

    /**
     * retourne une chaine contenant les information necessaire au reporting
     * @return 
     */
    public String reporting() {
        Optional<Association> assoReturn = this.associationInterface.findById((long) 1);
        if (!assoReturn.isPresent()) {
            //TODO exception 
        }
        Association a = assoReturn.get();
        return "{\"cotisationMin\" : \""+a.getCotisationMin()+"\"}";
    }
     
     
    
}
