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
import java.security.InvalidParameterException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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
import java.util.logging.Level;
import java.util.logging.Logger;
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

    /**
     * Permet de créer une nouvelle randonnée
     *
     * @param rando
     * @return
     */
    public Randonnee createRando(Randonnee rando) throws ParseException {
        //vérifications des données

        //vérifier si les dates sont  valides
        String pattern = "yyyy-MM-dd";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        String date = simpleDateFormat.format(new Date());
        LocalDate today = LocalDate.parse(date,DateTimeFormatter.ofPattern(pattern));
/*
        LocalDate dateCreneau1 = LocalDate.parse(rando.getDate1(),DateTimeFormatter.ofPattern(pattern));
        LocalDate dateCreneau2 = LocalDate.parse(rando.getDate2(),DateTimeFormatter.ofPattern(pattern));
        LocalDate dateCreneau3 = LocalDate.parse(rando.getDate3(),DateTimeFormatter.ofPattern(pattern));

       

        Boolean datesValides = (dateCreneau1.compareTo(today) > 0) && (dateCreneau2.compareTo(today) > 0) && (dateCreneau3.compareTo(today) > 0);
        System.out.println(datesValides);
        if (!datesValides) {
            throw new InvalidParameterException("Date(s) crénaux doit être dans le futur");
        }
        */
        //organisateur apte : vérifier que la personne qui veut créer la rando est un organisateur + il a un niveau 1,5 ...
        Long idTL = rando.getIdTeamLeader();
        int niveauTL = 0;
        int distanceRando = Integer.parseInt(rando.getDistanceR());
        RestTemplate restTemplate = new RestTemplate();
        String fooResourceUrl = "http://localhost:8080/api/randoMembre/";
        ResponseEntity<String> response = restTemplate.getForEntity(fooResourceUrl + idTL, String.class);

        ObjectMapper mapper = new ObjectMapper();
        JsonNode root;
        try {
            root = mapper.readTree(response.getBody());
            JsonNode niveauM = root.path("niveauM");            
            niveauTL = Integer.parseInt(niveauM.asText());
        } catch (IOException ex) {
            Logger.getLogger(GestionRandonnee.class.getName()).log(Level.SEVERE, null, ex);
        }

        //vérif distance et niveau TL
        if (distanceRando > (niveauTL * 1.5)) {
            throw new InvalidParameterException("Votre niveau ne vous permet pas d organiser cette randonnee");
        }

        //verif cout
        float budgetAssociation = 0;
        float budgetRando = rando.getCoutFixeR();

        RestTemplate restTemplate2 = new RestTemplate();
        String fooResourceUrl2 = "http://localhost:8080/api/randoAsso";
        ResponseEntity<String> response2 = restTemplate2.getForEntity(fooResourceUrl2, String.class);

        ObjectMapper mapper2 = new ObjectMapper();
        JsonNode root2;
        try {
            root2 = mapper2.readTree(response2.getBody());
            JsonNode budgetAsso = root2.path("budgetAsso");
            budgetAssociation = Float.parseFloat(budgetAsso.asText());
        } catch (IOException ex) {
            Logger.getLogger(GestionRandonnee.class.getName()).log(Level.SEVERE, null, ex);
        }

        if (budgetAssociation < budgetRando) {
            throw new InvalidParameterException("Le budget de l association n est pas en mesure de couvrir cette randonnée");
        }

        Randonnee initRando = new Randonnee(rando.getTitreR(), rando.getNiveauCible(), rando.getIdTeamLeader(), rando.getLieuR(), rando.getDistanceR(), rando.getCoutFixeR(), rando.getCoutVariableR(), rando.getDate1(), rando.getDate2(), rando.getDate3());
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
/**
 * Permet de cloturer les votes d'une rando
 * @param id 
 */
    public void cloturerVotes(String id) {
        Randonnee randoReturn = this.randoInterface.findById(id).get();
        if (randoReturn != null) {
            //cloturer les votes
            randoReturn.setSondageCloture(true);

            //màj de la rando
            randoInterface.save(randoReturn);
        }
    }

    /**
     * Permet au team leader de cloturer une inscription à une rando dont l'id
     * est passé en paramètre
     *
     * @param id
     */
    public void cloturerInscription(String id) {

        Randonnee randoReturn = this.randoInterface.findById(id).get();
        DateTimeFormatter formater = DateTimeFormatter.ofPattern("dd-MM-yyyy", Locale.ENGLISH);

        if (randoReturn != null) {
            //cloturer les votes et les inscris
            randoReturn.setInscriCloture(true);
            randoReturn.setSondageCloture(true);

            //fixer la date de la rando selon les votes
            String dateElue = dateApresVotes(randoReturn);
            System.out.println("date elue: " + dateApresVotes(randoReturn));
            randoReturn.setDateRando(dateElue);

            //affacter toute personne ayant voté pour la date "finale" de la rando après cloture
            inscrireVoteurs(randoReturn);

            //inscrire automatiquement le TL
            List<Long> idInscris = randoReturn.getListInscris();
            if (!idInscris.contains(randoReturn.getIdTeamLeader())) {
                idInscris.add(randoReturn.getIdTeamLeader());
            }

            //màj le budget de l'association : soustraire le cout total de la rando + coutFixe
            Long coutTotal = randoReturn.getCoutFixeR() + randoReturn.getCoutVariableR() * idInscris.size();

            RestTemplate restTemplate = new RestTemplate();
            String fooResourceUrl = "http://localhost:8080/api/randoAsso/financerRando/";
            ResponseEntity<String> response = restTemplate.getForEntity(fooResourceUrl + coutTotal, String.class);

            //màj de la rando
            randoInterface.save(randoReturn);
        }
    }

    /**
     * Après une cloture d'inscription: permet d'inscrire automatiquement tous
     * ceux qui ont voté pour la date élue
     *
     * @param rando
     */
    public void inscrireVoteurs(Randonnee rando) {
        HashMap votes = rando.getVotesR();

        ArrayList<Long> idVoteurs = (ArrayList<Long>) votes.get(rando.getDateRando());
        List<Long> idInscris = rando.getListInscris();

        for (int i = 0; i < idVoteurs.size(); i++) {

            //vérifier si le membre qui a voté n'est pas déja inscri
            Long idVoteur = idVoteurs.get(i);
            if (!idInscris.contains(idVoteur)) {
                //ajouter le membre à la liste des inscris
                idInscris.add(idVoteur);
            }
        }
    }

    public void modifierBudgetAssociation() {

    }

    /**
     * Détermine la date qui a le plus de votes
     *
     * @param r
     * @return la date Elue
     */
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

    /**
     * Permet à un membre de voter à un créneau d'une randonnée
     *
     * @param idRando
     * @param idMembre
     * @param dateChoisie
     * @throws IOException
     */
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
                throw new InvalidParameterException("Date créneau not found");
            }
        }
    }

    /**
     * Permet à un membre de s'inscrire à une rando
     *
     * @param idRando
     * @param idMembre
     */
    public void inscriptionRando(String idRando, long idMembre) {
        //chercher la rando

        Optional<Randonnee> randoReturn = this.randoInterface.findById(idRando);

        //vérifier que le membre existe
        RestTemplate restTemplate = new RestTemplate();
        String fooResourceUrl = "http://localhost:8080/api/randoMembre/";
        ResponseEntity<String> response = restTemplate.getForEntity(fooResourceUrl + idMembre, String.class);

        //vérifier le code réponse : si il est égale à  200 OK
        Boolean memberExist = response.getStatusCode().equals(HttpStatus.OK);

        if (!memberExist) {
            throw new InvalidParameterException("Le membre n existe pas");
        }

        if (randoReturn.isPresent()) {
            Randonnee rando = randoReturn.get();

            if (rando.isOverBooked()) {
                throw new InvalidParameterException("Il ne reste plus de place dans cette randonnee");
            }
            //vérifier que les votes sont cloturés et que l'inscription est encore ouverte et qu il reste des places
            if (!rando.getInscriCloture() && !rando.getSondageCloture() && !rando.isOverBooked() && memberExist) {

                rando.ajouterMembreInscri(idMembre);

                //màj rando
                randoInterface.save(rando);
            }
        } else {
            throw new InvalidParameterException("La rando que vous cherchez n existe pas");
        }
    }

    /**
     * Renvoie la list des randos aux quelles le membre ne s est pas inscri
     * (inscription non cloturée)
     *
     * @param idMembre
     * @return
     * @throws IOException
     */
    public List<Randonnee> getRandoInscriNonCloturePourUnMembre(Long idMembre) throws IOException {
        //list to retur init
        List<Randonnee> randosToReturn = new ArrayList<Randonnee>();

        //vérifier que le membre existe
        RestTemplate restTemplate = new RestTemplate();
        String fooResourceUrl = "http://localhost:8080/api/randoMembre/";
        ResponseEntity<String> response = restTemplate.getForEntity(fooResourceUrl + idMembre, String.class);

        //vérifier le code réponse : si il est égale à  200 OK
        Boolean memberExist = response.getStatusCode().equals(HttpStatus.OK);
        if (memberExist) {
            ObjectMapper mapper = new ObjectMapper();
            JsonNode root = mapper.readTree(response.getBody());
            JsonNode isApte = root.path("isApte");

            //vérifier si le membre est apte avant de s inscrire
            if (isApte.asBoolean()) {

                List<Randonnee> randosInscisOuvertes = this.randoInterface.findByInscriClotureAndSondageCloture(false, true);

                for (int i = 0; i < randosInscisOuvertes.size(); i++) {
                    Randonnee rando = randosInscisOuvertes.get(i);
                    List<Long> idInscrits = rando.getListInscris();
                    //vérifier s il reste des places dispo pour la rando

                    if (!rando.isOverBooked()) {
                        //ajouter la rando à laquelle le membre ne s'es pas inscrit
                        if (!idInscrits.contains(idMembre)) {                            
                            randosToReturn.add(randosInscisOuvertes.get(i));
                        }
                    }
                }
            } else {
                throw new InvalidParameterException("Nous somme désolés, vous n etes pas apte à vous inscrire");
            }
        } else {
            throw new InvalidParameterException("LE membre n existe pas");
        }
        return randosToReturn;
    }

    /**
     * Transfomre le retour data mondo en un String
     *
     * @param randos
     * @return
     */
    public String convertDataToString(List<Randonnee> randos) {
        String result = "[";
        for (int i = 0; i < randos.size(); i++) {
            result += (String) randos.get(i).toString() + ",";
        }
        if (randos.size() > 0) {
            result = result.substring(0, result.length() - 1);
        }
        return result + "]";
    }

    public List<Randonnee> getRandoInscriNonCloture() {
        return this.randoInterface.findByInscriCloture(false);
    }

    public List<Randonnee> getRandoVotesNonClotureCréeParUnTL(Long idTL) {
        return this.randoInterface.findByIdTeamLeaderAndSondageCloture(idTL, false);
    }

    public List<Randonnee> getRandoInsciNonClotureCréeParUnTL(Long idTL) {
        return this.randoInterface.findByIdTeamLeaderAndInscriCloture(idTL, false);
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

            //parcourir les votes de chaque rando: chaque ligne dans la map sera appele vote = <date, arrayList<idMember>>
            Boolean exist = false;
            while (it.hasNext() && !exist) {
                HashMap.Entry vote = (HashMap.Entry) it.next();

                ArrayList<Long> idMembers = (ArrayList<Long>) vote.getValue();

                //vérifier si l id du membre existe dans une des 3 liste de votes
                if (idMembers.contains(idMembre)) {
                    exist = true;
                }
                it.remove();
            }
            //ajouter la rando à laquelle le membre n a pas voté
            if (!exist) {
                randosToReturn.add(allRandoWithOpenVotes.get(i));
            }

        }
        return randosToReturn;
    }

    public String reporting() {
        List<Randonnee> listRandonnee = (List<Randonnee>) randoInterface.findAll();
        float totalcoutrando = 0;
        float encour = 0;
        int nbRandoPos = 0;
        for (Randonnee randonnee : listRandonnee) {
            totalcoutrando += randonnee.getCoutFixeR();
            int nbparticipant = randonnee.getListInscris().size();
            float coutvar = randonnee.getCoutVariableR();
            totalcoutrando += nbparticipant * coutvar;

            if (randonnee.getDateRando() != null) {
                nbRandoPos++;
            } else {
                encour += randonnee.getCoutFixeR();
            }
        }
        return "{\"totalCoutRando\" : \"" + totalcoutrando + "\", \"nbRandoPos\" : \"" + nbRandoPos + "\", \"encour\" : \"" + encour + "\"  }";
    }

}
