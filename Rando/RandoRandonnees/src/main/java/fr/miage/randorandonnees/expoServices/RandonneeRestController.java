package fr.miage.randorandonnees.expoServices;

import ch.qos.logback.core.net.ObjectWriter;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.util.JSON;
import fr.miage.randorandonnees.entities.Randonnee;
import fr.miage.randorandonnees.metier.GestionRandonnee;
import static java.lang.Long.parseLong;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
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
    public List<Randonnee> getRandos(){
        return (List<Randonnee>) this.gestRando.getAllRando();
    } 
    
    @GetMapping("/{randoId}")
    public String getRandonnee(@PathVariable("randoId") String id) {
        //return this.gestRando.getRandoById(id).getNbPlaces();
        System.out.println(this.gestRando.getRandoById(id).toString());
        return this.gestRando.getRandoById(id).toString();
    }
    
    @PostMapping
    public Randonnee creerRandonnee(@RequestBody Randonnee rando) {
        System.out.println(rando.getTitreR());
        return this.gestRando.createRando(rando);
    }
    
@PutMapping("/{randoId}")
    public void majRando(@PathVariable("randoId") String id, @RequestBody Randonnee rando) {
        this.gestRando.majRando(id, rando);
    }
   /* 

    @PatchMapping("/cloturerVotes/{randoId}")
    public void cloturerVotes(@PathVariable("randoId") String id) {
        this.gestRando.cloturerVotes(Long.parseLong(id));
    }

    @PatchMapping("/cloturerInscription/{randoId}")
    public void cloturerInscription(@PathVariable("randoId") String id) {
        this.gestRando.cloturerInscription(Long.parseLong(id));
    }

    @PatchMapping("/voterCreneau/{randoId}")
    public void voterCreneau(@PathVariable("randoId") String idRando, String idMembre, String participation, String dateChoisie) {
        SimpleDateFormat formater = new SimpleDateFormat("dd-MM-yyyy");
        try {
            Date dateFormated = formater.parse(dateChoisie);
            this.gestRando.voterCreneau(Long.parseLong(idRando), Long.parseLong(idMembre), dateFormated);
        } catch (ParseException ex) {
            Logger.getLogger(RandonneeRestController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }*/

  /*  @GetMapping
    public List<Randonnee> getRandoPassees() {
        return (List<Randonnee>) this.gestRando.getRandoPassees();
    }
    
        @GetMapping
    public List<Randonnee> getCouTotalRandos() {
        return (List<Randonnee>) this.gestRando.getCouTotalRandos();
    }*/
}
