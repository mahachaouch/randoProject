/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.miage.randomembre.expoServices;

import fr.miage.randomembre.entities.Association;
import fr.miage.randomembre.metier.GestionAssociation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Maha
 */
@RestController
@RequestMapping("/api/randoAsso")
public class AssociationRestController {   
        
    @Autowired
    private GestionAssociation gestAsso;
    
    @CrossOrigin
    @GetMapping
    public Association  getBudgetAsso(){
        return this.gestAsso.getAssociation();
    }
    
    @CrossOrigin
    @PostMapping
    public Association createAssociation(@RequestBody Association association){
        return this.gestAsso.createAssociation(association);
    }
    
    @CrossOrigin
    @GetMapping("/financerRando")
    public void financerRando(@RequestParam("cout") String cout){
        float coutR = Float.parseFloat(cout);
        this.gestAsso.financerRando(coutR);
    }
    
    @CrossOrigin
    @GetMapping("/reporting")
    public String reporting(){
        return this.gestAsso.reporting();
    }
}
