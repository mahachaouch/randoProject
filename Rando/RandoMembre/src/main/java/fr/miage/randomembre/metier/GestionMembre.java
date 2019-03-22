/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.miage.randomembre.metier;

import fr.miage.randomembre.entities.Membre;
import fr.miage.randomembre.repositories.MembreInterface;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author Maha
 */
public class GestionMembre {
    @Autowired
    private MembreInterface membreInterface;
    
    public List<Membre> getMembres(){
        return (List<Membre>) membreInterface.findAll();
    }

    public List<Membre> getMembre(Long id) {
        Optional<Membre> membreReturn =  this.membreInterface.findById(id);
        if(!membreReturn.isPresent()){
            
        }
        return  (List<Membre>) membreReturn.get();
    }
    
    
}
