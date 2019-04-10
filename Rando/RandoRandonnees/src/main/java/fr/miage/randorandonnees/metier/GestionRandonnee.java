/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.miage.randorandonnees.metier;

import fr.miage.randorandonnees.entities.Randonnee;
import fr.miage.randorandonnees.repositories.RandonneeInterface;
import static java.lang.Long.parseLong;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import javax.management.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.client.RestTemplate;

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
    	//vérifications des données
    	
    	//dates valides
    	Date today = new Date();
    	Boolean datesValides = rando.getDate1().equals(today) && rando.getDate2().equals(today) && rando.getDate3().equals(today);
    	
    	//organisateur apte
    	Boolean orgOk = true;
    	 final String uri = "http://localhost:8080/membre.json";
         
    	    RestTemplate restTemplate = new RestTemplate();
    	    String result = restTemplate.getForObject(uri, String.class);
    	   // result.
    	
    	//vérifier cout
    	Boolean coutOk= true;
    	
    	if (datesValides && orgOk && coutOk) {
    	    return this.randoInterface.save(rando);
    	}else {
    		return null;
    	}
    }

    //prend en paramètre une randonnée contenant les modification appaortées aux paramètres 
    public void majRando(Long id,Randonnee rando) {
        
        Randonnee randoReturn =  this.randoInterface.findById(id).get();
      
        //Randonnee r = mongoOperations
        if(randoReturn != null){
            //copie tous les attribue de rando dans randoToUpdate
        	randoReturn.setTitreR(rando.getTitreR());
        	randoReturn.setCoutFixeR(rando.getCoutFixeR());
        	randoReturn.setCoutVariableR(rando.getCoutVariableR());
        	
        	//les 3 dates pour le sondage
        	randoReturn.setDate1(rando.getDate1());
        	randoReturn.setDate2(rando.getDate2());
        	randoReturn.setDate3(rando.getDate3());
        	
        	randoReturn.setDistanceR(rando.getDistanceR());
        	randoReturn.setIdTeamLeader(rando.getIdTeamLeader());
        	randoReturn.setLieuR(rando.getLieuR());
        	randoReturn.setNiveauCible(rando.getNiveauCible());
        	
        	//sauvegarde de la mise à jour des params
        	randoInterface.save(randoReturn);
        }
    }

	public void cloturerVotes(Long id) {
		// TODO Auto-generated method stub
		Randonnee randoReturn =  this.randoInterface.findById(id).get();
		if(randoReturn != null) {
			//cloturer les votes
			randoReturn.setSondageCloture(true);
			
			//màj de la rando
			randoInterface.save(randoReturn);
		}		
	}

	public void cloturerInscription(long id) {
		// TODO Auto-generated method stub
		Randonnee randoReturn =  this.randoInterface.findById(id).get();
		if(randoReturn != null) {
			//cloturer les votes
			randoReturn.setInscriCloture(true);
			
			//màj de la rando
			randoInterface.save(randoReturn);
		}
	}
   
}
