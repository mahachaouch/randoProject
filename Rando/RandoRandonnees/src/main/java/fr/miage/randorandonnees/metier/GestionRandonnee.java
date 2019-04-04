/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.miage.randorandonnees.metier;

import fr.miage.randorandonnees.entities.Randonnee;
import fr.miage.randorandonnees.repositories.RandonneeInterface;
import static java.lang.Long.parseLong;
import java.util.List;
import java.util.Optional;
import javax.management.Query;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author Maha
 */
public class GestionRandonnee {
    @Autowired
    private RandonneeInterface randoInterface;
        
    public List<Randonnee> getAllMembres(){
        return (List<Randonnee>) randoInterface.findAll();
    }
    
    public Randonnee getRandoById(Long id){
        Optional<Randonnee> randoReturn =  this.randoInterface.findById(id);
        if(randoReturn.get() != null){
        }
        return  randoReturn.get();       
    }

    public Randonnee createRando(Randonnee rando) {
    return this.randoInterface.save(rando);
    }

    public void majRando(Randonnee rando) {
        
        Optional<Randonnee> randoReturn =  this.randoInterface.findById(rando.getId());
        Query query = new Query();
        //Randonnee r = mongoOperations
        if(randoReturn.get() != null){
            //copie tous les attribue de rando dans randoToUpdate
          // List<Randonnee> randoArrayResult = (List<Randonnee>) randoReturn.get();
          
        }
    }
    
    
}
