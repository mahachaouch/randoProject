/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.miage.randomembre.expoServices;

import fr.miage.randomembre.entities.Membre;
import fr.miage.randomembre.metier.GestionAssociation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 *
 * @author Maha
 */
public class AssociationRestController {   
        
    @Autowired
    private GestionAssociation gestAsso;
    
        @GetMapping(value="/{assoId}")
    public Membre  getBudgetAsso(@PathVariable("assoId") String id){
        Long idAsso = Long.parseLong(id);
        return this.gestAsso.getAssociation(idAsso);
    }
}
