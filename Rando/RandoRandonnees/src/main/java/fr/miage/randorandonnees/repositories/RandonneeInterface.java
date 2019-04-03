/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.miage.randorandonnees.repositories;

import fr.miage.randorandonnees.entities.Randonnee;
import org.springframework.data.repository.CrudRepository;

/**
 *
 * @author Maha
 */
public interface RandonneeInterface  extends CrudRepository<Randonnee,Long> {
    
}
