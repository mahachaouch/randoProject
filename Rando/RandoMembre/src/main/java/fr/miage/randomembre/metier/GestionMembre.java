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

    public Membre getMembre(Long id) {
        Optional<Membre> membreReturn =  this.membreInterface.findById(id);
        if(!membreReturn.isPresent()){
           //TODO exception 
        }
        return membreReturn.get();
    }

    public List<Membre> findMembresByType(String type) {
        Optional<Membre> membreReturn = null;
        switch(type) {
            case "TL":
              membreReturn =  this.membreInterface.findByIsTLIsTrue();
              break;
            case "President":
              membreReturn =  this.membreInterface.findByIsPresidentIsTrue();
              break;
            case "Secretaire":
              membreReturn =  this.membreInterface.findByIsSecretaireIsTrue();
              break;
            default:
              //TODO exception 
          }
        return  (List<Membre>) membreReturn.get();
    }

    public Membre createMembre(Membre membre) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void updateMembre(Membre membre) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void deleteMembre(Long idMembre) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    
}
