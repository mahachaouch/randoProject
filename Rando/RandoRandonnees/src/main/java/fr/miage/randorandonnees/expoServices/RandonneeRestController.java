package fr.miage.randorandonnees.expoServices;

import ch.qos.logback.core.net.ObjectWriter;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.util.JSON;
import fr.miage.randorandonnees.entities.Randonnee;
import fr.miage.randorandonnees.metier.GestionRandonnee;
import java.io.IOException;
import static java.lang.Long.parseLong;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
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

    @GetMapping
    public String getRandos() {
        List<Randonnee> randos = this.gestRando.getAllRando();
        // System.out.println(this.gestRando.convertDataToString( this.gestRando.getAllRando()));
        return this.gestRando.convertDataToString(randos);
    }

    @GetMapping("/randoToVotes")
    public String getRandosWithOpenVotes() {
        List<Randonnee> randos = this.gestRando.getRandoVoteNonCloture();
        return this.gestRando.convertDataToString(randos);
    }

    @GetMapping("/randoInscriNonCloture")
    public String getRandosInsciNonCloture() {
        List<Randonnee> randos = this.gestRando.getRandoInscriNonCloture();
        return this.gestRando.convertDataToString(randos);
    }

    @GetMapping("/{randoId}")
    public String getRandonnee(@PathVariable("randoId") String id) {
        //return this.gestRando.getRandoById(id).getNbPlaces();
        System.out.println(this.gestRando.getRandoById(id).toString());
        return this.gestRando.getRandoById(id).toString();
    }

    //getRandoInscriNonCloture
    @PostMapping
    public Randonnee creerRandonnee(@RequestBody Randonnee rando) {
        System.out.println(rando.getTitreR());
        return this.gestRando.createRando(rando);
    }

    @PutMapping("/{randoId}")
    public void majRando(@PathVariable("randoId") String id, @RequestBody Randonnee rando) {
        this.gestRando.majRando(id, rando);
    }

    @PatchMapping("/cloturerVotes/{randoId}")
    public void cloturerVotes(@PathVariable("randoId") String id) {
        this.gestRando.cloturerVotes(id);
    }

    @PatchMapping("/cloturerInscription/{randoId}")
    public void cloturerInscription(@PathVariable("randoId") String id) {
        this.gestRando.cloturerInscription(id);
    }

    @PatchMapping("/voterCreneau/{randoId}")
    public void voterCreneau(@PathVariable("randoId") String idRando, String idMembre, String dateChoisie) throws IOException {
        DateTimeFormatter formater = DateTimeFormatter.ofPattern("dd-MM-yyyy", Locale.ENGLISH);
        LocalDate dateFormated = LocalDate.parse(dateChoisie, formater);
        System.out.println(dateFormated);
        this.gestRando.voterCreneau(idRando, Long.parseLong(idMembre), dateFormated);
    }

    /*  @GetMapping
    public List<Randonnee> getRandoPassees() {
        return (List<Randonnee>) this.gestRando.getRandoPassees();
    }
    
        @GetMapping
    public List<Randonnee> getCouTotalRandos() {
        return (List<Randonnee>) this.gestRando.getCouTotalRandos();
    }*/
}
