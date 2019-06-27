package fr.miage.randorandonnees.expoServices;

import ch.qos.logback.core.net.ObjectWriter;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.util.JSON;
import fr.miage.randorandonnees.entities.Randonnee;
import fr.miage.randorandonnees.metier.GestionRandonnee;
import java.io.IOException;
import static java.lang.Long.parseLong;
import java.security.InvalidParameterException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Maha
 */
@RestController
@RequestMapping("api/randonnee")
public class RandonneeRestController {

    @Autowired
    private GestionRandonnee gestRando;

    @CrossOrigin
    @GetMapping
    public String getRandos() {
        List<Randonnee> randos = this.gestRando.getAllRando();
        return this.gestRando.convertDataToString(randos);
    }

    @CrossOrigin
    @GetMapping("/randoToVotes")
    public String getRandosWithOpenVotes() {
        List<Randonnee> randos = this.gestRando.getRandoVoteNonCloture();
        return this.gestRando.convertDataToString(randos);
    }
/**
 * renvoie la liste des randos dont les votes sont pas cloturés et auxquelles le membre n a pas encore voté
 * @param id
 * @return 
 */
    @CrossOrigin
    @GetMapping("/randoToVotes/{idMembre}")
    public String getRandosWithOpenVotes(@PathVariable("idMembre") String id) {
        List<Randonnee> randos = this.gestRando.getRandoVotesNonClotureNonVoteParMembre(Long.parseLong(id));
        return this.gestRando.convertDataToString(randos);
    }
/**
 * renvoie toutes les randos dont l inscri n est pas cloturée
 * @return 
 */
    @CrossOrigin
    @GetMapping("/randoInscriNonCloture")
    public String getRandosInsciNonCloture() {
        List<Randonnee> randos = this.gestRando.getRandoInscriNonCloture();
        return this.gestRando.convertDataToString(randos);
    }
/**
 * renvoie toutes les randos dont l inscri n est pas cloturée et à la quelle le membre ne s est pas inscrit
 * @param id
 * @return
 * @throws IOException 
 */
    @CrossOrigin
    @GetMapping("/randoInscriNonCloture/{idMembre}")
    public String getRandosInsciNonClotureForAspecificMember(@PathVariable("idMembre") String id) throws IOException {
        List<Randonnee> randos = this.gestRando.getRandoInscriNonCloturePourUnMembre(Long.parseLong(id));
        return this.gestRando.convertDataToString(randos);
    }
/**
 * renvoie toutes les randos dont les votes ne sont pas cloturées, organisées par le TL (id en param)
 * @param id
 * @return
 * @throws IOException 
 */
    @CrossOrigin
    @GetMapping("/randoVotesACloturer/{idTL}")
    public String getRandoAcloturerVotes(@PathVariable("idTL") String id) throws IOException {
        List<Randonnee> randos = this.gestRando.getRandoVotesNonClotureCréeParUnTL(Long.parseLong(id));
        return this.gestRando.convertDataToString(randos);
    }
/**
 * renvoie toutes les randos dont les inscriptions ne sont pas cloturées, organisées par le TL (id en param)
 * @param id
 * @return
 * @throws IOException 
 */
    @CrossOrigin
    @GetMapping("/randoInscisACloturer/{idTL}")
    public String getRandoAcloturerInscris(@PathVariable("idTL") String id) throws IOException {
        List<Randonnee> randos = this.gestRando.getRandoInsciNonClotureCréeParUnTL(Long.parseLong(id));
        return this.gestRando.convertDataToString(randos);
    }

    @CrossOrigin
    @GetMapping("/{randoId}")
    public String getRandonnee(@PathVariable("randoId") String id) {
        return this.gestRando.getRandoById(id).toString();
    }

    @CrossOrigin
    @PostMapping
    public Randonnee creerRandonnee(@RequestBody Randonnee rando) throws ParseException {        
        return this.gestRando.createRando(rando);
    }
/**
 * met à jour une randoonée
 * @param id
 * @param rando 
 */
    @CrossOrigin
    @PutMapping("/{randoId}")
    public void majRando(@PathVariable("randoId") String id, @RequestBody Randonnee rando) {
        this.gestRando.majRando(id, rando);
    }
/**
 * Cloture les votes de la rando dont l id est donnée en param
 * @param id 
 */
    @CrossOrigin
    @PatchMapping("/cloturerVotes/{randoId}")
    public void cloturerVotes(@PathVariable("randoId") String id) {
        this.gestRando.cloturerVotes(id);
    }

    @CrossOrigin
    @PatchMapping("/cloturerInscription/{randoId}")
    public void cloturerInscription(@PathVariable("randoId") String id) {
        this.gestRando.cloturerInscription(id);
    }

    @CrossOrigin
    @PatchMapping("/voterCreneau/{randoId}")
    public void voterCreneau(@PathVariable("randoId") String idRando, String idMembre, String dateChoisie) throws IOException {
        this.gestRando.voterCreneau(idRando, Long.parseLong(idMembre), dateChoisie);
    }

    @CrossOrigin
    @PatchMapping("/inscriptionRando/{randoId}")
    public void inscriptionRando(@PathVariable("randoId") String idRando, String idMembre) {
        this.gestRando.inscriptionRando(idRando, Long.parseLong(idMembre));
    }
    
    @CrossOrigin
    @GetMapping("/reporting")
    public String reporting(){
        return this.gestRando.reporting();
    }
    
    @ExceptionHandler(InvalidParameterException.class)
    public ResponseEntity<String> paramManquant(HttpServletRequest requete, NumberFormatException ex) {
        return new ResponseEntity<>("paramètre(s) manquant(s)", HttpStatus.UNPROCESSABLE_ENTITY);
    }
    
   
}
