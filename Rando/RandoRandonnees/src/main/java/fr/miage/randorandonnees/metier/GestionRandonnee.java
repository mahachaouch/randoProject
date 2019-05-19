/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.miage.randorandonnees.metier;

import fr.miage.randorandonnees.entities.Randonnee;
import fr.miage.randorandonnees.repositories.RandonneeInterface;
import static java.lang.Long.parseLong;
import java.util.ArrayList;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import javax.management.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.client.RestTemplate;

/**
 *
 * @author Maha
 */
@Controller
public class GestionRandonnee {

    @Autowired
    private RandonneeInterface randoInterface;
    
    public List<Randonnee> getAllRando() {
        return (List<Randonnee>) randoInterface.findAll();
    }

    public Randonnee getRandoById(String id) {
        Optional<Randonnee> randoReturn = this.randoInterface.findById(id);
       // if (randoReturn.get() != null) {
        //}
        return randoReturn.get();
    }

    public Randonnee createRando(Randonnee rando) {
        //vérifications des données

        //dates valides
        Date today = new Date();
        Boolean datesValides = rando.getDate1().equals(today) && rando.getDate2().equals(today) && rando.getDate3().equals(today);

        //organisateur apte : vérifier que la personne qui veut créer la rando est un organisateur + il a un niveau 1,5 ...
        //coté angular
        //vérifier cout
        
       // return this.randoInterface.save(rando);
       /* if (datesValides) {
            Randonnee initRando = new Randonnee(rando.getTitreR(),rando.getNiveauCible(),rando.getIdTeamLeader(),rando.getLieuR(),rando.getDistanceR(),rando.getCoutFixeR(),rando.getCoutVariableR(),rando.getDate1(),rando.getDate2(),rando.getDate3());
            System.out.println(initRando.toString());
            return this.randoInterface.save(initRando);
            
          //  return this.randoInterface.save(rando);
        } else {
            return null;
        }*/
          Randonnee initRando = new Randonnee(rando.getTitreR(),rando.getNiveauCible(),rando.getIdTeamLeader(),rando.getLieuR(),rando.getDistanceR(),rando.getCoutFixeR(),rando.getCoutVariableR(),rando.getDate1(),rando.getDate2(),rando.getDate3());
            System.out.println(initRando.toString());
            return this.randoInterface.save(initRando);
    }

    //prend en paramètre une randonnée contenant les modification appaortées aux paramètres 
    public void majRando(String id, Randonnee rando) {

        Randonnee randoReturn = this.randoInterface.findById(id).get();

        //Randonnee r = mongoOperations
        if (randoReturn != null) {
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

    public void cloturerVotes(String id) {
        Randonnee randoReturn = this.randoInterface.findById(id).get();
        if (randoReturn != null) {
            //cloturer les votes
            randoReturn.setSondageCloture(true);

            //màj de la rando
            randoInterface.save(randoReturn);
        }
    }

    public void cloturerInscription(String id) {

        Randonnee randoReturn = this.randoInterface.findById(id).get();
        if (randoReturn != null) {
            //cloturer les votes
            randoReturn.setInscriCloture(true);
            
            //fixer la date de la rando selon les votes
            randoReturn.setDateRando(dateApresVotes(randoReturn));

            //màj de la rando
            randoInterface.save(randoReturn);
        }
    }
    
    //détermine la date qui a gagné les votes du sondage
    public Date dateApresVotes(Randonnee r){
        //get Max votes 
        Integer nbVotes1 = r.getVotesR().get(r.getDate1()).size();
        Integer nbVotes2 = r.getVotesR().get(r.getDate2()).size();
        Integer nbVotes3 = r.getVotesR().get(r.getDate3()).size();
        
        Integer maxVotes = Math.max(nbVotes1, Math.max(nbVotes2,nbVotes3));
        
        if(nbVotes1.equals(maxVotes)){
            return r.getDate1();
        }
        
        if(nbVotes2.equals(maxVotes)){
            return r.getDate2();
        }
        
        if(nbVotes3.equals(maxVotes)){
            return r.getDate3();
        }
        return null;
    }

    public void voterCreneau(String idRando, long idMembre, Date dateChoisie) {
        //chercher la rando
        Randonnee randoReturn = this.randoInterface.findById(idRando).get();
        if (randoReturn != null) {
            HashMap votes = randoReturn.getVotesR();
            
            //verifier si la date envoyée est bien dans les propositions + le sondage n est pas cloturé
            if(votes.containsKey(dateChoisie)&& !randoReturn.getSondageCloture()){
                //ajouter l id du membre dans la liste correspendante
                ArrayList<Long> listM =  (ArrayList<Long>) votes.get(dateChoisie);
                listM.add(idMembre);
            }        
        }
    }
    
    //inscription d un membre à une rando
    public void inscriptionRando(String idRando, long idMembre) {
        //chercher la rando
        Randonnee randoReturn = this.randoInterface.findById(idRando).get();
        
        //il faut chercher coté angular le membre via API => vérifier si il a le niveau requis
        if (randoReturn != null) {
                        
            //vérifier que les votes sont cloturés et que l'inscription est encore ouverte et qu il reste des places
            if(!randoReturn.getInscriCloture() && randoReturn.getSondageCloture()&& !randoReturn.isOverBooked()){
                randoReturn.ajouterMembreInscri(idMembre);
                
                //màj rando
                randoInterface.save(randoReturn);
            }               
        }
    }
    
    public List<Randonnee> getRandoPassees(){
        List<Randonnee> randoFinished = new ArrayList<Randonnee>();
        List<Randonnee> randoReturn = this.randoInterface.findAll();
        
        for(int i=0;i<randoReturn.size();i++){
            Date today = new Date();
            Randonnee r = randoReturn.get(i);
            if(r.getDateRando().compareTo(today)> 0){
                randoFinished.add(r);
            }
        }        
        return randoFinished;
    }
    
    public List<Randonnee> getCouTotalRandos(){
        //TO DO
        return null;
    }
    
    public String convertDataToString(List<Randonnee> randos){
        String result = "[";
        for(int i=0;i< randos.size();i++){
            result+= (String)randos.get(i).toString() +",";
        }
        result = result.substring(0, result.length() - 1);
        return result+"]";
    }

    public List<Randonnee> getRandoVoteNonCloture() {
        return  this.randoInterface.findByInscriCloture(false);
    }
            
}
