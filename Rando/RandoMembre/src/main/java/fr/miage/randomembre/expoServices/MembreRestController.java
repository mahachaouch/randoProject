/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.miage.randomembre.expoServices;

import fr.miage.randomembre.entities.Membre;
import fr.miage.randomembre.metier.GestionMembre;
import java.util.List;

import org.apache.tomcat.util.http.parser.MediaType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
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

/**
 *
 * @author Maha
 */
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
    @RequestMapping(value = "/{memId}/get", method = RequestMethod.GET,produces =org.springframework.http.MediaType.APPLICATION_JSON_VALUE)
    public Membre getMemberById(@PathVariable(value = "memId") final String memId) {
    	return this.gestMembre.getMembre(Long.parseLong(memId));
    }
    
    @CrossOrigin
    @GetMapping(params = "type")
    public List<Membre> getMembresByType(@RequestParam(value="type") String type){
        return this.gestMembre.findMembresByType(type);
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
    
    
}
