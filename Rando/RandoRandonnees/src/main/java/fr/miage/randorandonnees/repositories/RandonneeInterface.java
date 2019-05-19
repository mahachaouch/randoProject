/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.miage.randorandonnees.repositories;

import fr.miage.randorandonnees.entities.Randonnee;
import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Maha
 */
@Repository
public interface RandonneeInterface  extends MongoRepository<Randonnee,String> {
    List<Randonnee> findByInscriCloture(Boolean cloture);
     List<Randonnee> findBySondageCloture(Boolean cloture);
    
}
