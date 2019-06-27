/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.miage.randomembre.metier;

import fr.miage.randomembre.entities.Association;
import fr.miage.randomembre.entities.Membre;
import fr.miage.randomembre.repositories.AssociationInterface;
import fr.miage.randomembre.repositories.MembreInterface;
import java.security.InvalidParameterException;
import java.util.Calendar;
import static java.util.Calendar.YEAR;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class GestionMembre {

    @Autowired
    private MembreInterface membreInterface;

    @Autowired
    private AssociationInterface assoInterface;

    /**
     * retourne la liste des membre enregistré dans le repo
     * @return 
     */
    public List<Membre> getMembres() {
        return (List<Membre>) membreInterface.findAll();
    }

    /**
     * retourne pour un id donné le membre contenu en repo correspondant, si il existe
     * @param id
     * @return 
     */
    public Membre getMembre(Long id) {
        Optional<Membre> membreReturn = this.membreInterface.findById(id);
        if (!membreReturn.isPresent()) {
            throw new InvalidParameterException("l'id fournis ne corrspond à aucun membre ");
        }
        return membreReturn.get();
    }

    /**
     * creer et ajoute le membre en parametre au repo
     * le login doit etre unique 
     * les login et mdp ne peuvent etre vide
     * @param membre
     * @return 
     */
    public Membre createMembre(Membre membre) {
        Optional<Membre> membreReturn = this.membreInterface.findByLoginM(membre.getLoginM());
        if (membreReturn.isPresent()) {
            throw new InvalidParameterException("login deja pris ");
        }
        if (membre.getLoginM() == "" || membre.getMdpM() == ""){
            throw new InvalidParameterException("les login et mdp deuvent etre dif de vide ");
        }
        return this.membreInterface.save(membre);
    }

    /**
     * met à jour le membre en repo
     * le membre doit déjà etre présent dans le repo
     * @param membre 
     */
    public void updateMembre(Membre membre) {

        Optional<Membre> membreReturn = this.membreInterface.findById(membre.getIdM());
        if (!membreReturn.isPresent()) {
            throw new InvalidParameterException("le membre fournis ne correspond à aucun membre present dans la base ");
        }
        this.membreInterface.save(membre);
    }

    /**
     * supprime du repo le membre d'id idMembre
     * l'id fournis doit correspondre à un membre
     * @param idMembre 
     */
    public void deleteMembre(Long idMembre) {
        Optional<Membre> membreReturn = this.membreInterface.findById(idMembre);
        if (!membreReturn.isPresent()) {
            throw new InvalidParameterException("l'id fournis ne corrspond à aucun membre ");
        }
        Membre m = membreReturn.get();
        this.membreInterface.delete(m);
    }

    /**
     * payer la cotisation du membre idMembre via son iban et la cotisation payée
     * l'iban doit etre different de vide
     * l'idmembre doit correspondre à un membre
     * la cotisation doit etre sup ou egal au min de l'asso
     * @param idMembre
     * @param iban
     * @param cotisation 
     */
    public void payerCotisation(long idMembre, String iban, Long cotisation) {
        Optional<Membre> membreReturn = this.membreInterface.findById(idMembre);
        Optional<Association> assoReturn = this.assoInterface.findById((long)1);
        
        if (iban.equals("")){
            throw new InvalidParameterException("un iban doit etre renseigné");
        }
        

        if (!membreReturn.isPresent()) {
            throw new InvalidParameterException("l'id fournis ne corrspond à aucun membre ");
        } else {
            Association asso = assoReturn.get();
            
            if (cotisation >= asso.getCotisationMin()) {

                Membre m = membreReturn.get();
                m.setIbanM(iban);
                m.setCotisationM(cotisation);
                m.setAnneeCotisation(new Date());

                asso.setBudgetAsso(asso.getBudgetAsso() + cotisation);
                this.assoInterface.save(asso);

                this.membreInterface.save(m);
            }else{
                throw new InvalidParameterException("le montant de la cotisation est insufisant : "+asso.getCotisationMin()+" minimum ");//TODO exception 
            }
            
        }
    }

    /**
     * maj la date de certif du membre d'id idMembre
     * idMembre doit correspondre à un membre
     * @param idMembre 
     */
    public void majCertifMedical(long idMembre) {
        Optional<Membre> membreReturn = this.membreInterface.findById(idMembre);
        if (!membreReturn.isPresent()) {
            throw new InvalidParameterException("l'id fournis ne correspond à aucun membre present dans la base "); 
        }
        Membre m = membreReturn.get();
        m.setAnneeCertificat(new Date());
        this.membreInterface.save(m);
    }
    
    /**
     * retourne une chaine contenant les information necessaire au reporting
     * @return 
     */
    public String reporting() {
        List<Membre> listMembre = (List<Membre>) membreInterface.findAll();
        int nbMembre = 0;
        int nbTL = 0;
        long totalCotisationRegle = 0;
        for (Membre membre : listMembre) {
            nbMembre++;
            if (membre.getIsTL()){
                nbTL++;
            }
            Calendar cal1 = Calendar.getInstance(Locale.US);
            cal1.setTime(membre.getAnneeCotisation());
            Calendar cal2 = Calendar.getInstance(Locale.US);
            cal2.setTime(new java.util.Date());
            
            if (cal1.get(YEAR) == cal2.get(YEAR)){
                totalCotisationRegle += membre.getCotisationM();
            }
        }
        return "{\"nbMembre\" : \""+nbMembre+"\",\"nbTL\" : \""+nbTL+"\",\"totalCotisationRegle\" : \""+totalCotisationRegle+"\"}";

        
    }

    /**
     * retourne, si il existe, le membre ayant les identifiants passé en parametre
     * @param loginM
     * @param mdpM
     * @return 
     */
    public Membre connexion(String loginM, String mdpM) {
        Optional<Membre> membreReturn = this.membreInterface.findMembreByLoginMAndMdpM(loginM,mdpM);
        if (!membreReturn.isPresent()) {
            throw new InvalidParameterException("login mdp incorrect");
        }else{
            Membre m = membreReturn.get();
            return m;
        }
        
    }

}
