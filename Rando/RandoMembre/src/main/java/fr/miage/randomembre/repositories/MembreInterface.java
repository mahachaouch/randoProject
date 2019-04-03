/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.miage.randomembre.repositories;

import fr.miage.randomembre.entities.Membre;
import java.util.Optional;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Maha
 */
@Repository
public interface MembreInterface extends CrudRepository<Membre,Long>{

    public Optional<Membre> findByIsTLIsTrue();

    public Optional<Membre> findByIsPresidentIsTrue();

    public Optional<Membre> findByIsSecretaireIsTrue();
    
}
