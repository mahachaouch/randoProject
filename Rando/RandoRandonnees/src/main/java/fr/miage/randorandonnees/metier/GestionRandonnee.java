/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.miage.randorandonnees.metier;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import fr.miage.randorandonnees.entities.Randonnee;
import fr.miage.randorandonnees.repositories.RandonneeInterface;
import java.io.IOException;
import static java.lang.Long.parseLong;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import static java.time.temporal.TemporalQueries.localDate;
import java.util.ArrayList;

import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import javax.management.Query;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
        Randonnee initRando = new Randonnee(rando.getTitreR(), rando.getNiveauCible(), rando.getIdTeamLeader(), rando.getLieuR(), rando.getDistanceR(), rando.getCoutFixeR(), rando.getCoutVariableR(), rando.getDate1(), rando.getDate2(), rando.getDate3());
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
        DateTimeFormatter formater = DateTimeFormatter.ofPattern("dd-MM-yyyy", Locale.ENGLISH);

        if (randoReturn != null) {
            //cloturer les votes
            randoReturn.setInscriCloture(true);

            //fixer la date de la rando selon les votes
            LocalDate dateFormated = LocalDate.parse(dateApresVotes(randoReturn), formater);
            Date date = Date.from(dateFormated.atStartOfDay(ZoneId.systemDefault()).toInstant());
            randoReturn.setDateRando(date);

            //màj de la rando
            randoInterface.save(randoReturn);
        }
    }

    //détermine la date qui a gagné les votes du sondage
    public String dateApresVotes(Randonnee r) {
        //get Max votes 
        Integer nbVotes1 = r.getVotesR().get(r.getDate1()).size();
        Integer nbVotes2 = r.getVotesR().get(r.getDate2()).size();
        Integer nbVotes3 = r.getVotesR().get(r.getDate3()).size();

        Integer maxVotes = Math.max(nbVotes1, Math.max(nbVotes2, nbVotes3));

        if (nbVotes1.equals(maxVotes)) {
            return r.getDate1();
        }

        if (nbVotes2.equals(maxVotes)) {
            return r.getDate2();
        }

        if (nbVotes3.equals(maxVotes)) {
            return r.getDate3();
        }
        return null;
    }

    public void voterCreneau(String idRando, long idMembre, String dateChoisie) throws IOException {

        //chercher la rando
        Optional<Randonnee> randoReturn = this.randoInterface.findById(idRando);

        if (randoReturn.isPresent()) {

            //vérifier que le membre existe
            RestTemplate restTemplate = new RestTemplate();
            String fooResourceUrl = "http://localhost:8080/api/randoMembre/";
            ResponseEntity<String> response = restTemplate.getForEntity(fooResourceUrl + idMembre, String.class);

            System.out.println(response.getBody());
            //vérifier le code réponse : si il est égale à  200 OK
            Boolean memberExist = response.getStatusCode().equals(HttpStatus.OK);

            Randonnee rando = randoReturn.get();
            HashMap votes = rando.getVotesR();

            //verifier si la date envoyée est bien dans les propositions + le sondage n est pas cloturé
            if (memberExist && votes.containsKey(dateChoisie) && !rando.getSondageCloture()) {

                //ajouter l id du membre dans la liste correspendante
                ArrayList<Long> listM = (ArrayList<Long>) votes.get(dateChoisie);

                listM.add(idMembre);
                randoInterface.save(rando);

            } else {
                System.out.println("date not found");
            }
        }
    }

    //inscription d un membre à une rando
    public void inscriptionRando(String idRando, long idMembre) {
        //chercher la rando

        Optional<Randonnee> randoReturn = this.randoInterface.findById(idRando);

        //vérifier que le membre existe
            RestTemplate restTemplate = new RestTemplate();
            String fooResourceUrl = "http://localhost:8080/api/randoMembre/";
            ResponseEntity<String> response = restTemplate.getForEntity(fooResourceUrl + idMembre, String.class);

            //vérifier le code réponse : si il est égale à  200 OK
            Boolean memberExist = response.getStatusCode().equals(HttpStatus.OK);
            
        //il faut chercher coté angular le membre via API => vérifier si il a le niveau requis
        if (randoReturn.isPresent()) {
            Randonnee rando = randoReturn.get();
            //vérifier que les votes sont cloturés et que l'inscription est encore ouverte et qu il reste des places
            if (!rando.getInscriCloture() && !rando.getSondageCloture() && !rando.isOverBooked() && memberExist) {
                
                rando.ajouterMembreInscri(idMembre);

                //màj rando
                randoInterface.save(rando);
            }
        }else{
            System.out.println("rando not found");
        }
    }

    public List<Randonnee> getRandoPassees() {
        List<Randonnee> randoFinished = new ArrayList<Randonnee>();
        List<Randonnee> randoReturn = this.randoInterface.findAll();

        for (int i = 0; i < randoReturn.size(); i++) {
            Date today = new Date();
            Randonnee r = randoReturn.get(i);
            if (r.getDateRando().compareTo(today) > 0) {
                randoFinished.add(r);
            }
        }
        return randoFinished;
    }

    public List<Randonnee> getCouTotalRandos() {
        //TO DO
        return null;
    }

    public String convertDataToString(List<Randonnee> randos) {
        String result = "[";
        for (int i = 0; i < randos.size(); i++) {
            result += (String) randos.get(i).toString() + ",";
        }
        result = result.substring(0, result.length() - 1);
        return result + "]";
    }

    public List<Randonnee> getRandoInscriNonCloture() {
        return this.randoInterface.findByInscriCloture(false);
    }

    public List<Randonnee> getRandoVoteNonCloture() {
        return this.randoInterface.findBySondageCloture(false);
    }

    //renvoie les randonnees dont le vote n est pas cloturé et aux quelles, le membre n'a pas encore voté
    public List<Randonnee> getRandoVotesNonClotureNonVoteParMembre(Long idMembre) {
        List<Randonnee> allRandoWithOpenVotes = this.randoInterface.findBySondageCloture(false);
        List<Randonnee> randosToReturn = new ArrayList<Randonnee>();

        for (int i = 0; i < allRandoWithOpenVotes.size(); i++) {
            HashMap votes = allRandoWithOpenVotes.get(i).getVotesR();
            Iterator it = votes.entrySet().iterator();

            //parcourir les votes de chaques rando: chaque ligne dans la map sera appele vote = <date, arrayList<idMember>>
            Boolean exist = false;
            while (it.hasNext() && !exist) {
                HashMap.Entry vote = (HashMap.Entry) it.next();
                // System.out.println(vote.getKey() + " = " + vote.getValue());
                ArrayList<Long> idMembers = (ArrayList<Long>) vote.getValue();

                //vérifier si l id du membre existe dans une des 3 liste de votes
                if (idMembers.contains(idMembre)) {
                    exist = true;
                }
                it.remove();
            }
            //ajouter la rando à laquelle le membre n a pas voté
            if (!exist) {
                //System.out.println("rando to return"+i);
                randosToReturn.add(allRandoWithOpenVotes.get(i));
            }

        }
        // System.out.println("final return" + randosToReturn.toString());
        return randosToReturn;
    }
}
