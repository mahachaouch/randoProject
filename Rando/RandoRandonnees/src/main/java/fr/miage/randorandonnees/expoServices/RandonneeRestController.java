package fr.miage.randorandonnees.expoServices;

import fr.miage.randorandonnees.entities.Randonnee;
import fr.miage.randorandonnees.metier.GestionRandonnee;
import static java.lang.Long.parseLong;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
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
    
    private GestionRandonnee gestRando;
    
    @GetMapping("/{randoId}")
    public Randonnee getRandonnee(@PathVariable("randoId") String id){
        return this.gestRando.getRandoById(parseLong(id));
    }
    
    @PostMapping
    public Randonnee creerRandonnee(@RequestBody Randonnee rando){
        return this.gestRando.createRando(rando);
    }
    
    @PutMapping("/{randoId}")
    public void majRando(@PathVariable("randoId") String id, @RequestBody Randonnee rando){
            this.gestRando.majRando(Long.parseLong(id),rando);
    } 
    
    @PatchMapping("/cloturerVotes/{randoId}")
    public void cloturerVotes(@PathVariable("randoId") String id) {
    	this.gestRando.cloturerVotes(Long.parseLong(id));
    }
    
    @PatchMapping("/cloturerInscription/{randoId}")
    public void cloturerInscription(@PathVariable("randoId") String id) {
    	this.gestRando.cloturerInscription(Long.parseLong(id));
    }
    
    @PatchMapping("/voterCreneau/{randoId}")
    public void voterCreneau(@PathVariable("randoId") String idRando, String idMembre, String participation, String dateChoisie){
        SimpleDateFormat formater = new SimpleDateFormat("dd-MM-yyyy");
        try {
            Date dateFormated = formater.parse(dateChoisie);
        this.gestRando.voterCreneau(Long.parseLong(idRando),Long.parseLong(idMembre),dateFormated);
        } catch (ParseException ex) {
            Logger.getLogger(RandonneeRestController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}