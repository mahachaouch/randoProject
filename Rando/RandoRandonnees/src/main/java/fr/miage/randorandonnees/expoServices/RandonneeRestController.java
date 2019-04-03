/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.miage.randorandonnees.expoServices;

import fr.miage.randorandonnees.entities.Randonnee;
import fr.miage.randorandonnees.metier.GestionRandonnee;
import static java.lang.Long.parseLong;
import org.springframework.web.bind.annotation.GetMapping;
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
            this.gestRando.majRando(rando);
    } 
}
