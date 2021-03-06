/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.miage.randomembre.expoServices;

import fr.miage.randomembre.entities.Membre;
import fr.miage.randomembre.metier.GestionMembre;
import java.security.InvalidParameterException;
import java.util.List;
import javax.servlet.http.HttpServletRequest;

import org.apache.tomcat.util.http.parser.MediaType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/randoMembre")
public class MembreRestController {
    
    @Autowired
    private GestionMembre gestMembre;
    
    @CrossOrigin
    @GetMapping
    public List<Membre> getMembres(){
        return (List<Membre>) this.gestMembre.getMembres();
    } 
    
    @CrossOrigin
    @GetMapping(value="/{membreId}")
    public Membre  getMembre(@PathVariable("membreId") String id){
        Long idMembre = Long.parseLong(id);
        return this.gestMembre.getMembre(idMembre);
    } 
    
    @CrossOrigin
    @PostMapping
    public Membre createMembre(@RequestBody Membre membre){
        return this.gestMembre.createMembre(membre);
    }
    
    @CrossOrigin
    @PutMapping
    public void majMembre(@RequestBody Membre membre){
        this.gestMembre.updateMembre(membre);
    }
    
    @CrossOrigin
    @DeleteMapping("/{membreId}")
    public void deleteMembre(@PathVariable("membreId") String id){
        Long idMembre = Long.parseLong(id);
        this.gestMembre.deleteMembre(idMembre);
    }
    
    @CrossOrigin
    @PatchMapping("/payerCotisation")
    public void payerCotisation(@RequestParam("idM") String idM, @RequestParam("iban") String iban, @RequestParam("cotisation") String cotisation){
        long idMembre = Long.parseLong(idM);
        long cotisationp = Long.parseLong(cotisation);
        this.gestMembre.payerCotisation(idMembre,iban,cotisationp);
    }
    
    @CrossOrigin
    @PatchMapping("/majCertifMedical")
    public void majCertifMedical(@RequestParam("idM") String idM){
        long idMembre = Long.parseLong(idM);
        this.gestMembre.majCertifMedical(idMembre);
    }
    
    @CrossOrigin
    @GetMapping("/reporting")
    public String reporting(){
        return this.gestMembre.reporting();
    }
    
    @CrossOrigin
    @GetMapping("/connexion")
    public Membre connexion(@RequestParam("loginM") String loginM, @RequestParam("mdpM") String mdpM){
        return this.gestMembre.connexion(loginM,mdpM);
    }
    
    @ExceptionHandler(InvalidParameterException.class)
    public ResponseEntity<String> invalidParam(HttpServletRequest requete, NumberFormatException ex) {
        return new ResponseEntity<>("paramètre(s) invalide(s)", HttpStatus.UNPROCESSABLE_ENTITY);
    }
    
}
