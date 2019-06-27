/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.miage.randomembre.repositories;

import fr.miage.randomembre.entities.Membre;
import java.util.Optional;
import org.springframework.data.jpa.repository.Query;
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
    
    @Query("SELECT m FROM Membre m WHERE m.loginM = ?1 and m.mdpM = ?2")
    public Optional<Membre> findMembreByLoginMAndMdpM(String loginM, String mdpM);

    public Optional<Membre> findByLoginM(String loginM);
    
}
