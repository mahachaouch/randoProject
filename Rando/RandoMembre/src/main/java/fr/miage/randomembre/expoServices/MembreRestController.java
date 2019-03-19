/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.miage.randomembre.expoServices;

import fr.miage.randomembre.entities.Membre;
import fr.miage.randomembre.metier.GestionMembre;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Maha
 */
@RestController
@RequestMapping("api/membres")
public class MembreRestController {
    
    private GestionMembre gestMembre;
    
    @GetMapping
    public List<Membre> getAllComptes(){
        return (List<Membre>) this.gestMembre.getAllMembres();
    } 
}
