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
    
    @GetMapping
    public List<Membre> getMembres(){
        return (List<Membre>) this.gestMembre.getMembres();
    } 
    
    @GetMapping(value="/{membreId}")
    public Membre  getMembre(@PathVariable("membreId") String id){
        Long idMembre = Long.parseLong(id);
        return this.gestMembre.getMembre(idMembre);
    } 
    
    @RequestMapping(value = "/{memId}/get", method = RequestMethod.GET,produces =org.springframework.http.MediaType.APPLICATION_JSON_VALUE)
    public Membre getMemberById(@PathVariable(value = "memId") final String memId) {
    	return this.gestMembre.getMembre(Long.parseLong(memId));
    }
    
    @GetMapping(params = "type")
    public List<Membre> getMembresByType(@RequestParam(value="type") String type){
        return this.gestMembre.findMembresByType(type);
    }
    
    @PostMapping
    public Membre createMembre(@RequestBody Membre membre){
        return this.gestMembre.createMembre(membre);
    }
    
    @PutMapping("/{membreId}")
    public void majMembre(@PathVariable("membreId") String id, @RequestBody Membre membre){
        Long idMembre = Long.parseLong(id);
        this.gestMembre.updateMembre(membre);
    }
    
    @DeleteMapping("/{membreId}")
    public void deleteMembre(@PathVariable("membreId") String id){
        Long idMembre = Long.parseLong(id);
        this.gestMembre.deleteMembre(idMembre);
    }
    
    @PatchMapping("/payerCotisation/{membreId}")
    public void payerCotisation(@PathVariable("membreId") String id, @RequestBody MultiValueMap<String, String> formParams){
        long idMembre = Long.parseLong(id);
        String iban = formParams.getFirst("iban");
        long cotisation = Long.parseLong(formParams.getFirst("cotisation"));
        this.gestMembre.payerCotisation(idMembre,iban,cotisation);
    }
    
    @PatchMapping("/majCertifMedical/{membreId}")
    public void majCertifMedical(@PathVariable("membreId") String id){
        long idMembre = Long.parseLong(id);
        this.gestMembre.majCertifMedical(idMembre);
    }
}
