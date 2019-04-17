/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.miage.randomembre.metier;

import fr.miage.randomembre.entities.Membre;
import fr.miage.randomembre.repositories.MembreInterface;
import java.util.Date;
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
        return this.membreInterface.save(membre);
    }

    public void updateMembre(Membre membre) {
        
        Optional<Membre> membreReturn =  this.membreInterface.findById(membre.getIdM());
        if(!membreReturn.isPresent()){
           //TODO exception 
        }
        Membre membreEnr = membreReturn.get();
        this.membreInterface.delete(membreEnr);
        this.membreInterface.save(membreEnr);
    }

    public void deleteMembre(Long idMembre) {
        Optional<Membre> membreReturn = this.membreInterface.findById(idMembre);
        if(!membreReturn.isPresent()){
           //TODO exception 
        }
        Membre m = membreReturn.get();
        this.membreInterface.delete(m);
    }

    public void payerCotisation(long idMembre, String iban, long cotisation) {
        Optional<Membre> membreReturn = this.membreInterface.findById(idMembre);
        if(!membreReturn.isPresent()){
           //TODO exception 
        }
        Membre m = membreReturn.get();
        m.setIbanM(iban);
        m.setCotisationM(cotisation);
        m.setAnneeCertificat(new Date());
        this.membreInterface.save(m);
    }
    
    public void majCertifMedical(long idMembre) {
        Optional<Membre> membreReturn = this.membreInterface.findById(idMembre);
        if(!membreReturn.isPresent()){
           //TODO exception 
        }
        Membre m = membreReturn.get();
        m.setAnneeCertificat(new Date());
        this.membreInterface.save(m);
    }
    
    
}
